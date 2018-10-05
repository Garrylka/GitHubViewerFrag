package ru.roskvartal.garry.githubviewerfrag.model;

import java.util.List;

import io.reactivex.Flowable;
import ru.roskvartal.garry.githubviewerfrag.entity.GitHubRepo;


//  Переход на RxJava 2 и List<GitHubRepo>.
public interface RepoModel {

    //  1) Загрузка данных.
    Flowable<List<GitHubRepo>> getRepos();

    //  2) Эмуляция задержки и ошибки при загрузке данных.
    Flowable<List<GitHubRepo>> getReposError();
}
