package ru.roskvartal.garry.githubviewerfrag.model;


import android.support.annotation.AnyThread;
import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

import ru.roskvartal.garry.githubviewerfrag.model.entity.GitHubRepoDetail;
import ru.roskvartal.garry.githubviewerfrag.model.entity.GitHubRepoMaster;


/**
 *  Добавление БД Realm.
 *  Переход на Retrofit/Gson.
 *  Т.к. сервер выдает пакет в 100 записей репозиториев пачкой, то перешел на Single<List<T>>.
 */
public interface RepoModel {

    //  Загрузка Мастер данных - список public репозиториев.

    @NonNull
    @AnyThread
    Observable<? extends List<GitHubRepoMaster>> lifecycleRealm();


    @NonNull
    @AnyThread
    Observable<? extends List<GitHubRepoMaster>> loadFromRealm();


    @NonNull
    @AnyThread
    Observable<? extends List<GitHubRepoMaster>> loadFromInet();


    //  Загрузка Детальных данных выбранного репозитория.

    @NonNull
    @AnyThread
    Single<GitHubRepoDetail> getRepoDetail(String ownerName, String repoName);


    //  Выборка репозитория по ID из БД Realm.
    @NonNull
    @AnyThread
    GitHubRepoMaster getRepoMasterById(int repoId);
}
