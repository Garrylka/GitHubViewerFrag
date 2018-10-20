package ru.roskvartal.garry.githubviewerfrag.di;


import android.support.annotation.NonNull;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import ru.roskvartal.garry.githubviewerfrag.model.api.GitHubService;


/**
 *  Подключил Dagger 2 для реализации паттерна DI.
 */
@Module
public class NetModule {

    private static final String NAMED_RETROFIT_ENDPOINT  = "RETROFIT_ENDPOINT";
    private static final String NAMED_RETROFIT_SCHEDULER = "RETROFIT_SCHEDULER";


    @Provides
    @NonNull
    @Named(NAMED_RETROFIT_ENDPOINT)
    public String provideEndpoint() {

        return GitHubService.API_ENDPOINT;
    }


    @Singleton
    @Provides
    @NonNull
    @Named(NAMED_RETROFIT_SCHEDULER)
    public Scheduler provideRetrofitScheduler() {

        return Schedulers.io();
    }


    @Singleton
    @Provides
    @NonNull
    public CallAdapter.Factory provideRetrofitCallAdapterFactory(
            @NonNull @Named(NAMED_RETROFIT_SCHEDULER) Scheduler scheduler) {

        return RxJava2CallAdapterFactory.createWithScheduler(scheduler);
    }


    @Singleton
    @Provides
    @NonNull
    Converter.Factory provideRetrofitConverterFactory() {

        return GsonConverterFactory.create();
    }


    @Singleton
    @Provides
    @NonNull
    public Retrofit provideRetrofit(
            @NonNull @Named(NAMED_RETROFIT_ENDPOINT) String baseUrl,
            @NonNull CallAdapter.Factory adapter,
            @NonNull Converter.Factory converter) {

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(adapter)
                .addConverterFactory(converter)
                .build();
    }


    @Singleton
    @Provides
    @NonNull
    GitHubService provideGitHubApi(@NonNull Retrofit retrofit) {

        return retrofit.create(GitHubService.class);
    }

}
