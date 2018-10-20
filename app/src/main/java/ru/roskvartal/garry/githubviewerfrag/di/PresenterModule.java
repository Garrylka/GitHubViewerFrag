package ru.roskvartal.garry.githubviewerfrag.di;


import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import ru.roskvartal.garry.githubviewerfrag.model.RepoModel;
import ru.roskvartal.garry.githubviewerfrag.presenter.ReposPresenter;
import ru.roskvartal.garry.githubviewerfrag.presenter.ReposPresenterImpl;


@Module(includes = ModelModule.class)
public class PresenterModule {

    @Singleton
    @Provides
    @NonNull
    public ReposPresenter providePresenter(@NonNull RepoModel model) {

        return new ReposPresenterImpl(model);
    }

}
