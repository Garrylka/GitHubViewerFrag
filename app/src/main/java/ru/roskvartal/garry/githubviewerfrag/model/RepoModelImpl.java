package ru.roskvartal.garry.githubviewerfrag.model;


import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import ru.roskvartal.garry.githubviewerfrag.model.api.GitHubService;
import ru.roskvartal.garry.githubviewerfrag.model.entity.GitHubRepoMaster;


/**
 *  Переход на Retrofit/Gson.
 *  Т.к. сервер выдает пакет в 100 записей репозиториев пачкой, то перешел на Single<List<T>>.
 */
public class RepoModelImpl implements RepoModel {

    @NonNull
    private final GitHubService restApi;


    //  Constructor.
    public RepoModelImpl(@NonNull GitHubService restApi) {

        this.restApi = restApi;
    }


    //  1) Загрузка Master данных - списка репозиториев.
    @Override
    public Single<List<GitHubRepoMaster>> getRepos() {

        return restApi.getRepos().subscribeOn(Schedulers.io());
    }

}
