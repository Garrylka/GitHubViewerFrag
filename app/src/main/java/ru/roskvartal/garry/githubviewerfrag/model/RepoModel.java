package ru.roskvartal.garry.githubviewerfrag.model;


import java.util.List;

import io.reactivex.Flowable;

import io.reactivex.Single;
import ru.roskvartal.garry.githubviewerfrag.model.entity.GitHubRepoMaster;


//  Переход на Retrofit/Gson.
public interface RepoModel {

    //  1) Загрузка списка репозиториев.
    Single<List<GitHubRepoMaster>> getRepos();

    //  2) Эмуляция задержки и ошибки при загрузке данных.
    //Flowable<List<GitHubRepo>> getReposError();
}
