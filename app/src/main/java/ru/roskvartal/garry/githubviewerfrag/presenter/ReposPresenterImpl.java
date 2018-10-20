package ru.roskvartal.garry.githubviewerfrag.presenter;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.util.Log;
import android.util.Pair;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

import java.util.List;

import io.reactivex.disposables.Disposable;

import ru.roskvartal.garry.githubviewerfrag.model.RepoModel;
import ru.roskvartal.garry.githubviewerfrag.view.ReposView;


/**
 *  Добавление БД Realm.
 *  Переход на Retrofit/Gson.
 *  Т.к. сервер выдает пакет в 100 записей репозиториев пачкой, то перешел на Single<List<T>>.
 *  Переход на RxJava 2 и List<T>.
 */
public class ReposPresenterImpl extends MvpBasePresenter<ReposView> implements ReposPresenter {

    private static final String LOGCAT_TAG = "LIST";            //  DEBUG

    private static final String ERR_INTERNET = "Ошибка при загрузке данных из Интернет!";


    @NonNull
    private final RepoModel model;                              //  MVP - Model.

    @Nullable
    private Disposable lifecycle;
    @Nullable
    private Disposable loading;


    //  Constructor.
    public ReposPresenterImpl(@NonNull RepoModel model) {
        this.model = model;
    }


    //  Приложение стартует и в attachView() подписывается через поток lifecycleRealm()
    //  на отложенный поток данных loadFromRealm(). Одновременно происходит подключение к БД Realm.
    //  Потом Mosby внутри себя дергает метод loadRepos() и выполняется подписка на потоки (дерганье)
    //  loadFromRealm() и loadFromInet().
    //  Загрузка данных из Интернет происходит в потоке loadFromInet(). Одновременно идет вставка и
    //  обновление данных в БД.
    //  При любом (?) изменении данных в БД данные (какие именно?) приходят в потоке loadFromRealm(),
    //  который передает их во VIEW через подписку на lifecycleRealm().
    //
    //  ACHTUNG! Проверить в каких случаях вызывается attachView()/detachView() с учетом Mosby ViewState!
    @Override
    public void attachView(@NonNull ReposView view) {

        super.attachView(view);

        Log.d(LOGCAT_TAG, "Presenter.attachView()!");

        if (!isSubscribed(lifecycle)) {

            Log.d(LOGCAT_TAG, "Subscribe.To.lifecycle()!");

            lifecycle = model.lifecycleRealm()
                    .filter(list -> !list.isEmpty())
                    .subscribe(
                            data -> {
                                //Log.d(LOGCAT_TAG, "lifecycle().onItem() on Thread: " + Thread.currentThread().getName());
                                Log.d(LOGCAT_TAG, "lifecycle().onNext(): " + data.size());

                                //  Отображение данных.
                                ifViewAttached(v -> v.setData(data));
                            },
                            error -> {
                                //Log.d(LOGCAT_TAG, "lifecycle().onError(): " + error.toString());

                                //  Вывод сообщения об ошибке.
                                ifViewAttached(v -> v.showError(error, false));
                            },
                            () -> {
                                //  ACHTUNG! Событие onComplete() никогда не наступит!
                                Log.d(LOGCAT_TAG, "lifecycle().onComplete()!");
                            }
                    );
        }
    }


    //  Загрузка Мастер данных - список public репозиториев.
    @Override
    public void loadRepos(final boolean pullToRefresh) {

        //Log.d(LOGCAT_TAG, "Presenter.loadRepos(" + pullToRefresh + ")!");

        unsubscribe(loading);

        Log.d(LOGCAT_TAG, "Subscribe.To.loading()!");

        loading = model.loadFromRealm()
                .map(List::isEmpty)
                .doOnNext(isEmpty ->
                        ifViewAttached(v -> v.showLoading(pullToRefresh)))
                .zipWith(model.loadFromInet()
                        .map(List::isEmpty)
                        .onErrorReturn(t -> true), Pair::create)
                .subscribe(
                        pair -> {
                            boolean isViewEmpty   = pair.first;
                            boolean isErrorCaused = pair.second;

                            if (isViewEmpty && isErrorCaused) {
                                ifViewAttached(v -> v.showError(new Throwable(ERR_INTERNET), pullToRefresh));
                            } else {
                                ifViewAttached(MvpLceView::showContent);
                                if (isErrorCaused) {
                                    ifViewAttached(v -> v.showError(new Throwable(ERR_INTERNET), true));
                                }
                            }
                        });
    }


    //  TEST!
    @Override
    public void detachView() {
        super.detachView();

        Log.d(LOGCAT_TAG, "Presenter.detachView()!");
    }


    //  Presenter унчтожается.
    @Override
    public void destroy() {
        Log.d(LOGCAT_TAG, "Presenter.destroy()!");

        //  Отписываемся от потоков данных.
        unsubscribe(lifecycle);
        unsubscribe(loading);

        super.destroy();
    }


    //  Отписка от потока.
    private void unsubscribe(@Nullable Disposable source) {

        if (isSubscribed(source)) {

            Log.d(LOGCAT_TAG, "UnSubscribe.From." + source.toString() + "!");

            source.dispose();
        }
    }


    //  Проверка подписки на поток.
    private boolean isSubscribed(@Nullable Disposable source) {

        return source != null && !source.isDisposed();
    }
}
