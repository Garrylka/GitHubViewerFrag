package ru.roskvartal.garry.githubviewerfrag.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import ru.roskvartal.garry.githubviewerfrag.view.ReposView;


//  Переход на Mosby MVP.
public interface ReposPresenter extends MvpPresenter<ReposView> {

    void loadRepos();
}
