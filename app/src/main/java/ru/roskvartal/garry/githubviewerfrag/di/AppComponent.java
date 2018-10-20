package ru.roskvartal.garry.githubviewerfrag.di;


import android.support.annotation.NonNull;
import android.widget.ImageView;

import javax.inject.Singleton;

import dagger.Component;

import ru.roskvartal.garry.githubviewerfrag.model.RepoModel;
import ru.roskvartal.garry.githubviewerfrag.model.image.ImageLoader;
import ru.roskvartal.garry.githubviewerfrag.presenter.ReposPresenter;


/**
 *  Подключил Dagger 2 для реализации паттерна DI.
 */
@Singleton
@Component(modules = {PresenterModule.class, ImageLoaderModule.class})
public interface AppComponent {

    //  Презентер.
    @NonNull
    ReposPresenter getMasterPresenter();

    //  Модель данных.
    @NonNull
    RepoModel getRepoModel();

    //  Загрузка изображений.
    @NonNull
    ImageLoader<ImageView> getImageLoader();
}
