package ru.roskvartal.garry.githubviewerfrag.presenter;

import ru.roskvartal.garry.githubviewerfrag.model.RepoModel;
import ru.roskvartal.garry.githubviewerfrag.view.ReposView;


//  Переход на MVP.
public class ReposPresenterImpl implements ReposPresenter {

    private final RepoModel model;


    public ReposPresenterImpl(RepoModel model) {
        this.model = model;
    }


    @Override
    public void loadRepos(ReposView view) {
        view.setRepos(model.getRepos());
        view.showContent();
    }
}
