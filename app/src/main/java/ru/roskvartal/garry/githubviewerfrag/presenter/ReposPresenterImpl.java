package ru.roskvartal.garry.githubviewerfrag.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import ru.roskvartal.garry.githubviewerfrag.model.RepoModel;
import ru.roskvartal.garry.githubviewerfrag.view.ReposView;


//  Переход на Mosby MVP.
public class ReposPresenterImpl extends MvpBasePresenter<ReposView> implements ReposPresenter {

    private final RepoModel model;


    public ReposPresenterImpl(RepoModel model) {
        this.model = model;
    }


    @Override
    public void loadRepos() {
        ifViewAttached(view -> {
            view.setRepos(model.getRepos());
            view.showContent();
        });
    }
}
