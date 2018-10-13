package ru.roskvartal.garry.githubviewerfrag.model;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

import io.realm.RealmResults;
import ru.roskvartal.garry.githubviewerfrag.model.api.GitHubService;
import ru.roskvartal.garry.githubviewerfrag.model.entity.GitHubRepoDetail;
import ru.roskvartal.garry.githubviewerfrag.model.entity.GitHubRepoMaster;


/**
 *  Добавление БД Realm.
 *  Переход на Retrofit/Gson.
 *  Т.к. сервер выдает пакет в 100 записей репозиториев пачкой, то перешел на Single<List<T>>.
 */
public class RepoModelImpl implements RepoModel {

    private static final String LOGCAT_TAG = "LIST";            //  DEBUG

    private static final String ERR_REALM_OPENED = "Соединение с Realm уже открыто!";
    private static final String ERR_REALM_CLOSED = "Нет соединения с Realm!";


    @NonNull
    private final GitHubService restApi;

    @Nullable
    private Realm realm ;

    //  Пока хардкодим тут, потом можно перенести в параметры конструктора.
    @NonNull
    private final Scheduler scheduler = AndroidSchedulers.mainThread();         //  Ограничения Realm!



    //  Constructor.
    public RepoModelImpl(@NonNull GitHubService restApi) {

        this.restApi = restApi;
    }


    //  Получаем экземпляр Realm (типа открываем сессию).
    //  Ресурсы надо освободить после завершения работы!
    private void openRealmSession() {

        Log.d(LOGCAT_TAG, "Model.openRealmSession() !!!");

        //  Новая конфигурация уже сделана дефолтной в MyApp.
        realm = Realm.getDefaultInstance();
    }


    //  Закрываем сессию Realm.
    private void closeRealmSession() {

        Log.d(LOGCAT_TAG, "Model.closeRealmSession() !!!");

        if (realm != null) {
            realm.close();
            realm = null;
        }
    }


    //  Проверка состояния сессии Realm.
    private boolean isRealmOpened() {

        return realm != null && !realm.isClosed();
    }



    //  Загрузка Мастер данных - список public репозиториев.


    //  Прежде, чем получать откуда-либо какие-либо данные, мы должны подписаться на этот поток данных
    //  и отписаться, когда работа завершена. Подписаться на этот поток данных можно лишь один раз!
    //  1) Управляем ресурсом Realm (типа открытие/закрытие соединения с БД).
    //  2) Получаем Observable потока данных из БД Realm.
    @NonNull
    @Override
    public Observable<? extends List<GitHubRepoMaster>> lifecycleRealm() {

        return Observable.defer(() -> {

            //  Проверяем состояние соединения с БД.
            if (isRealmOpened()) {
                throw new IllegalStateException(ERR_REALM_OPENED);
            }

            //  Получаем экземпляр Realm (открываем соединение с БД).
            openRealmSession();

            //  Отложенный поток данных из БД.
            return loadFromRealm();

        }).doOnDispose(() -> {

            Log.d(LOGCAT_TAG, "lifecycleRealm.doOnDispose() !!!");

            //  Проверяем состояние соединения с БД.
            if (!isRealmOpened()) {
                throw new IllegalStateException(ERR_REALM_CLOSED);
            }

            //  Закрываем соединение с БД.
            closeRealmSession();
        });
    }


    //  Формирует отложенный поток данных из БД Realm.
    //  Находим ВСЕ объекты GitHubRepoMaster и возвращаем асинхронно.
    //  Асинхронно можно только в потоке, на котором определён looper! ->
    //  Scheduler scheduler = AndroidSchedulers.mainThread() - главный поток UI.
    @NonNull
    @Override
    public Observable<? extends List<GitHubRepoMaster>> loadFromRealm() {

        return Observable.defer(() -> {

            //  Проверяем состояние соединения с БД.
            if (!isRealmOpened()) {
                throw new IllegalStateException(ERR_REALM_CLOSED);
            }

            //  Формируем запрос к БД.
            return realm.where(GitHubRepoMaster.class)
                    //.limit(10)                              //  А можно на этом этапе отсеять!
                    .sort("owner.ownerName")
                    .findAllAsync()                         //  Возвращает тип <? extends List<>>
                    .asFlowable()                           //  В Realm есть только этот каст! :(
                    .filter(RealmResults::isLoaded)         //  TODO Узнать, что значит эта проверка!
                    .toObservable();

        }).subscribeOn(scheduler);                          //  UI поток!
    }


    //  Формирует поток данных из внешнего источника и записывает в БД, если этих данных там еще нет.
    @NonNull
    @Override
    public Observable<? extends List<GitHubRepoMaster>> loadFromInet() {

        return restApi.getRepos()
                .observeOn(scheduler)               //  UI поток!
                .flatMapIterable(list -> list)      //  ACHTUNG! Метода flatMapIterable() нет в Single<>!
                .take(10)                           //  TEST!
                .toList()
                .doOnSuccess(repos -> {
                    Log.d(LOGCAT_TAG, "loadFromInet.doOnNext(): " + repos.size());

                    //  Проверяем состояние соединения с БД.
                    if (!isRealmOpened()) {
                        throw new IllegalStateException(ERR_REALM_CLOSED);
                    }

                    //  Выполняем транзакцию БД в асинхронном режиме.
                    realm.executeTransactionAsync( r -> {

                        //  Вставка списка объектов в БД.
                        //  Теперь данные всегда обновляются => приходят во VIEW!
                        r.insertOrUpdate(repos);

                    });
                })
                .toObservable();

/*
                .doOnNext(repoInet -> {
                    Log.d(LOGCAT_TAG, "loadFromInet.doOnNext(): " + repoInet.getRepoName());

                    //  Проверяем состояние соединения с БД.
                    if (!isRealmOpened()) {
                        throw new IllegalStateException(ERR_REALM_CLOSED);
                    }

                    //  Выполняем транзакцию БД в асинхронном режиме.
                    realm.executeTransactionAsync( r -> {

                        //  TODO Это все можно убрать! Оставить просто r.insertOrUpdate(repoInet); !

                        GitHubRepoMaster repoRealm = r.where(GitHubRepoMaster.class)
                                .equalTo("repoId", repoInet.getRepoId()).findFirst();

                        if (repoRealm == null) {

                            Log.d(LOGCAT_TAG, "loadFromInet.insertOrUpdate(): " + repoInet.getRepoName());

                            //  Вставка нового объекта в БД.
                            r.insertOrUpdate(repoInet);

                        } else {

                            String repoName = repoInet.getRepoName();
                            if (repoName != null && !repoName.equals(repoRealm.getRepoName())) {
                                repoRealm.setRepoName(repoName);
                            }

                            String repoDesc = repoInet.getRepoDesc();
                            if (repoDesc != null && !repoDesc.equals(repoRealm.getRepoDesc())) {
                                repoRealm.setRepoDesc(repoDesc);
                            }

                            String repoUrl = repoInet.getRepoUrl();
                            if (repoUrl != null && !repoUrl.equals(repoRealm.getRepoUrl())) {
                                repoRealm.setRepoUrl(repoUrl);
                            }
                        }
                    });
                })
                .toList()           //  Возвращает тип Single<List<>>
                .toObservable();
*/
    }


    //  Загрузка Детальных данных выбранного репозитория.
    @NonNull
    @Override
    public Single<GitHubRepoDetail> getRepoDetail(String ownerName, String repoName) {

        return restApi.getRepoDetail(ownerName, repoName).subscribeOn(Schedulers.io());
    }


    //  Выборка репозитория по ID из БД Realm.
    public GitHubRepoMaster getRepoMasterById(int repoId) {

        //  Проверяем состояние соединения с БД.
        if (!isRealmOpened()) {
            throw new IllegalStateException(ERR_REALM_CLOSED);
        }

        //  Получаем managed объект.
        GitHubRepoMaster repo = realm.where(GitHubRepoMaster.class)
                .equalTo("repoId", repoId).findFirst();

        //  Возвращаем unmanaged копию объекта.
        return realm.copyFromRealm(repo);
    }
}
