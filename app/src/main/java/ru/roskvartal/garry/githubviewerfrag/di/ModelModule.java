package ru.roskvartal.garry.githubviewerfrag.di;


import android.support.annotation.NonNull;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import io.reactivex.Scheduler;

import io.reactivex.android.schedulers.AndroidSchedulers;

import ru.roskvartal.garry.githubviewerfrag.model.RepoModel;
import ru.roskvartal.garry.githubviewerfrag.model.RepoModelImpl;
import ru.roskvartal.garry.githubviewerfrag.model.api.GitHubService;


@Module(includes = NetModule.class)
public class ModelModule {

    private static final String NAMED_DB_SCHEDULER = "DB_SCHEDULER";


    @Singleton
    @Provides
    @NonNull
    @Named(NAMED_DB_SCHEDULER)
    public Scheduler provideDbScheduler() {

        return AndroidSchedulers.mainThread();
    }


    @Singleton
    @Provides
    @NonNull
    public RepoModel provideModel(
            @NonNull GitHubService restApi,
            @NonNull @Named(NAMED_DB_SCHEDULER) Scheduler dbScheduler) {

        return new RepoModelImpl(restApi, dbScheduler);
    }
}
