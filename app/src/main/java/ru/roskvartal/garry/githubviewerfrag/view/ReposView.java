package ru.roskvartal.garry.githubviewerfrag.view;


import ru.roskvartal.garry.githubviewerfrag.entity.GitHubRepo;

//  Переход на MVP.
public interface ReposView {

    void setRepos(GitHubRepo[] repos);

    void showContent();
}
