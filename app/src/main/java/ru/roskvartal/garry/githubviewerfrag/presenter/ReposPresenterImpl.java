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


    //  TEST Тестирование:
    //  1) ProgressBar при помощи эмуляции задержки загрузки данных.
    @Override
    public void loadReposDefer(final boolean pullToRefresh) {

        ifViewAttached(view -> view.showLoading(pullToRefresh));

        model.getReposDefer(data -> ifViewAttached(view -> {
                view.setData(data);
                view.showContent();
            })
        );
    }


    //  2) Эмуляция задержки и ошибки при загрузке данных.
    @Override
    public void loadReposDeferError(final boolean pullToRefresh) {

        ifViewAttached(view -> view.showLoading(pullToRefresh));

        model.getReposDeferError(data -> {
            if (data != null) {
                ifViewAttached(view -> {
                    view.setData(data);
                    view.showContent();
                });
            } else {
                ifViewAttached(view -> view.showError(model.getError(), pullToRefresh));
            }
        });
    }


    //  3) Другой вариант эмуляции задержки и ошибки при загрузке данных.
    //  Используется два отдельных Action: для получения данных, при возникновении ошибки.
    public void loadReposDeferError2(final boolean pullToRefresh) {

        ifViewAttached(view -> view.showLoading(pullToRefresh));

        model.getReposDeferError2(new MyTestAction<GitHubRepo[]>() {
            @Override
            public void call(GitHubRepo[] data) {
                ifViewAttached(view -> {
                    view.setData(data);
                    view.showContent();
                });
            }
        }, new MyTestAction<Exception>() {
            @Override
            public void call(Exception e) {
                ifViewAttached(view -> view.showError(e, pullToRefresh));
            }
        });
    }
}
