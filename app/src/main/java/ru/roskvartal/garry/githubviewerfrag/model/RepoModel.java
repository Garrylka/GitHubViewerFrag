package ru.roskvartal.garry.githubviewerfrag.model;

import ru.roskvartal.garry.githubviewerfrag.entity.GitHubRepo;


//  Переход на MVP.
public interface RepoModel {

    GitHubRepo[] getRepos();

    GitHubRepo getRepoByPos(int position);

    GitHubRepo getRepoById(int repoId);

    int getReposCount();
}
