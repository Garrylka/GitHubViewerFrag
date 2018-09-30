package ru.roskvartal.garry.githubviewerfrag.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import ru.roskvartal.garry.githubviewerfrag.view.ReposView;


//  Переход на Mosby MVP LCE.
public interface ReposPresenter extends MvpPresenter<ReposView> {

    void loadRepos(final boolean pullToRefresh);

    //  TEST Тестирование:
    //  1) ProgressBar при помощи эмуляции задержки загрузки данных.
    void loadReposDefer(final boolean pullToRefresh);

    //  2) Эмуляция задержки и ошибки при загрузке данных.
    void loadReposDeferError(final boolean pullToRefresh);
}
