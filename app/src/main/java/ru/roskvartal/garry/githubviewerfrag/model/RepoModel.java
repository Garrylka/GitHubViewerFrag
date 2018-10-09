package ru.roskvartal.garry.githubviewerfrag.model;


import java.util.List;

import io.reactivex.Single;

import ru.roskvartal.garry.githubviewerfrag.model.entity.GitHubRepoMaster;


/**
 *  Переход на Retrofit/Gson.
 *  Т.к. сервер выдает пакет в 100 записей репозиториев пачкой, то перешел на Single<List<T>>.
 */
public interface RepoModel {

    //  1) Загрузка Master данных - списка репозиториев.
    Single<List<GitHubRepoMaster>> getRepos();
}
