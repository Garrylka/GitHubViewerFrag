package ru.roskvartal.garry.githubviewerfrag.model;


import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Function;

import io.reactivex.schedulers.Schedulers;
import ru.roskvartal.garry.githubviewerfrag.model.api.GitHubService;
import ru.roskvartal.garry.githubviewerfrag.model.entity.GitHubRepo;
import ru.roskvartal.garry.githubviewerfrag.model.entity.GitHubRepoMaster;


//  Переход на Retrofit/Gson.
public class RepoModelImpl implements RepoModel {

    //private static final int BUF_SIZE = 10;
    //private static final long DELAY_SEC = 2;
    //private static final float PCT_ERROR = 0.5f;


    //  1) Загрузка данных.
    //  Result fromArray() - Flowable<GitHubRepo>
    //  Result buffer()    - Flowable<List<GitHubRepo>>
    /*
    @Override
    public Flowable<List<GitHubRepo>> getRepos() {
        return Flowable.fromArray(GitHubRepo.repos).buffer(BUF_SIZE);
    }

    //  2) Эмуляция задержки и ошибки при загрузке данных.
    @Override
    public Flowable<List<GitHubRepo>> getReposError() {
        return Flowable
                .timer(DELAY_SEC, TimeUnit.SECONDS)
                .flatMap((Function<Long, Flowable<List<GitHubRepo>>>) aLong -> {
                    if (Math.random() > PCT_ERROR) {
                        return Flowable.error(new IOException());
                    }
                    return Flowable.fromArray(GitHubRepo.repos).buffer(BUF_SIZE);
                });
    }*/


    @NonNull
    private final GitHubService restApi;


    public RepoModelImpl(@NonNull GitHubService restApi) {

        this.restApi = restApi;
    }


    @Override
    public Single<List<GitHubRepoMaster>> getRepos() {

        return restApi.getRepos().subscribeOn(Schedulers.io());
    }


}
