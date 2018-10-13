package ru.roskvartal.garry.githubviewerfrag.presenter;


import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import ru.roskvartal.garry.githubviewerfrag.view.ReposView;


/**
 *  Переход на Retrofit/Gson.
 *  Переход на RxJava 2 и List<T>.
 */
public interface ReposPresenter extends MvpPresenter<ReposView> {

    //  Загрузка Master данных - списка репозиториев.
    void loadRepos(final boolean pullToRefresh);
}
