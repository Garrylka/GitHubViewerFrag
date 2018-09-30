package ru.roskvartal.garry.githubviewerfrag.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import ru.roskvartal.garry.githubviewerfrag.entity.GitHubRepo;
import ru.roskvartal.garry.githubviewerfrag.model.MyTestAction;
import ru.roskvartal.garry.githubviewerfrag.model.RepoModel;
import ru.roskvartal.garry.githubviewerfrag.view.ReposView;


//  Переход на Mosby MVP LCE.
public class ReposPresenterImpl extends MvpBasePresenter<ReposView> implements ReposPresenter {

    private final RepoModel model;


    public ReposPresenterImpl(RepoModel model) {
        this.model = model;
    }


    @Override
    public void loadRepos(final boolean pullToRefresh) {
        ifViewAttached(view -> {
            view.showLoading(pullToRefresh);
            view.setData(model.getRepos());
            view.showContent();
        });
    }


    //  TEST Тестирование ProgressBar при помощи эмуляции задержки загрузки данных.
    @Override
    public void loadReposDefer(boolean pullToRefresh) {

        ifViewAttached(view -> view.showLoading(pullToRefresh));

        model.getReposDefer(data -> ifViewAttached(view -> {
            view.setData(data);
            view.showContent();
        }));
    }
}
