package ru.roskvartal.garry.githubviewerfrag.view;


import com.hannesdorfmann.mosby3.mvp.MvpView;

import ru.roskvartal.garry.githubviewerfrag.entity.GitHubRepo;

//  Переход на Mosby MVP.
public interface ReposView extends MvpView {

    void setRepos(GitHubRepo[] repos);

    void showContent();
}
