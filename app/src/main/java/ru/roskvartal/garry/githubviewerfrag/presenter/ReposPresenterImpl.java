package ru.roskvartal.garry.githubviewerfrag.presenter;


import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import ru.roskvartal.garry.githubviewerfrag.model.RepoModel;
import ru.roskvartal.garry.githubviewerfrag.view.ReposView;


//  Переход на RxJava 2 и List<GitHubRepo>.
public class ReposPresenterImpl extends MvpBasePresenter<ReposView> implements ReposPresenter {

    private static final String LOGCAT_TAG    = "LIST";                         //  DEBUG

    private final RepoModel model;                                              //  MVP - Model.

    private Disposable disposable;



    public ReposPresenterImpl(RepoModel model) {
        this.model = model;
    }


    //  Presenter будет уничтожен.
    @Override
    public void destroy() {
        //  Отписываемся от потока данных.
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            disposable = null ;
        }

        super.destroy();
    }


    //  1) Загрузка данных - model.getRepos().
    //  2) Эмуляция задержки и ошибки при загрузке данных - model.getReposError().
    @Override
    public void loadRepos(final boolean pullToRefresh) {
        ifViewAttached(view -> {
            if (pullToRefresh) {
                view.clearViewData();
            }
            view.showLoading(pullToRefresh);
        });

        disposable = model.getReposError()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        data -> {
                            Log.d(LOGCAT_TAG, "doOnNext(): " + data.size());

                            ifViewAttached(view -> {
                                view.setData(data);
                                view.showContent();
                            });
                        },
                        e -> {
                            Log.d(LOGCAT_TAG, "doOnError(): " + e.toString());

                            ifViewAttached(view -> view.showError(e, pullToRefresh));
                        },
                        () -> ifViewAttached(
                            view -> Log.d(LOGCAT_TAG, "doOnComplete(): " + view.getDataCount())
                        )

                );
    }
}
