package ru.roskvartal.garry.githubviewerfrag.model;

import ru.roskvartal.garry.githubviewerfrag.entity.GitHubRepo;


//  Переход на Mosby MVP LCE.
public interface RepoModel {

    GitHubRepo[] getRepos();

    GitHubRepo getRepoByPos(int position);

    GitHubRepo getRepoById(int repoId);

    int getReposCount();

    //  TEST Тестирование:
    //  1) ProgressBar при помощи эмуляции задержки загрузки данных.
    void getReposDefer(final MyTestAction<GitHubRepo[]> onNext);

    //  2) Эмуляция ошибки при загрузке данных.
    void getReposDeferError(final MyTestAction<GitHubRepo[]> onNext);

    Throwable getError();
}
