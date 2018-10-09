package ru.roskvartal.garry.githubviewerfrag.presenter;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import ru.roskvartal.garry.githubviewerfrag.model.RepoModel;
import ru.roskvartal.garry.githubviewerfrag.view.ReposView;


/**
 *  Переход на Retrofit/Gson.
 *  Т.к. сервер выдает пакет в 100 записей репозиториев пачкой, то перешел на Single<List<T>>.
 *  Переход на RxJava 2 и List<T>.
 */
public class ReposPresenterImpl extends MvpBasePresenter<ReposView> implements ReposPresenter {

    private static final String LOGCAT_TAG = "LIST";            //  DEBUG

    @NonNull
    private final RepoModel model;                              //  MVP - Model.

    @Nullable
    private Disposable loading;


    //  Constructor.
    public ReposPresenterImpl(@NonNull RepoModel model) {
        this.model = model;
    }


    //  Presenter будет уничтожен.
    @Override
    public void destroy() {
        //  Отписываемся от потока данных.
        unsubscribe(loading);
        super.destroy();
    }


    //  1) Загрузка Master данных - списка репозиториев.
    @Override
    public void loadRepos(final boolean pullToRefresh) {

        loading = model.getRepos()
                //  TEST .doOnSuccess(d -> Log.d(LOGCAT_TAG, "doOnSuccess() on Thread: " + Thread.currentThread().getName()))
                .doOnSubscribe(d -> {
                    Log.d(LOGCAT_TAG, "doOnSubscribe() on Thread: " + Thread.currentThread().getName());

                    ifViewAttached(view -> {
                        view.clearData(pullToRefresh);
                        view.showLoading(pullToRefresh);
                    });
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        data -> {
                            Log.d(LOGCAT_TAG, "onSuccess() on Thread: " + Thread.currentThread().getName());
                            Log.d(LOGCAT_TAG, "onSuccess(): " + data.size());

                            ifViewAttached(view -> {
                                view.setData(data);
                                view.showContent();
                            });
                        },
                        error -> {
                            Log.d(LOGCAT_TAG, "onError(): " + error.toString());

                            ifViewAttached(view -> view.showError(error, pullToRefresh));
                        }
                        //,
                        //() -> ifViewAttached(
                        //    view -> Log.d(LOGCAT_TAG, "onComplete(): " + view.getDataCount())
                        //)
                );
    }


    private void unsubscribe(@Nullable Disposable disposable) {

        if (isSubscribed(disposable)) {
            disposable.dispose();
        }
    }


    private boolean isSubscribed(@Nullable Disposable disposable) {

        return disposable != null && !disposable.isDisposed();
    }
}
