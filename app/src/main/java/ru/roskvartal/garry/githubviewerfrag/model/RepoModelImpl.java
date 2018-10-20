package ru.roskvartal.garry.githubviewerfrag.model;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import io.realm.Realm;
import io.realm.RealmResults;

import ru.roskvartal.garry.githubviewerfrag.model.api.GitHubService;
import ru.roskvartal.garry.githubviewerfrag.model.entity.GitHubRepoDetail;
import ru.roskvartal.garry.githubviewerfrag.model.entity.GitHubRepoMaster;


/**
 *  Добавление БД Realm.
 *  Переход на Retrofit/Gson.
 *  Т.к. GitHub сервер выдает пакет в 100 репозиториев пачкой, то перешел на Single<List<T>>.
 */
public class RepoModelImpl implements RepoModel {

    private static final String LOGCAT_TAG = "LIST";            //  DEBUG

    private static final String ERR_REALM_OPENED = "Соединение с БД Realm уже открыто!";
    private static final String ERR_REALM_CLOSED = "Нет соединения с БД Realm!";
    private static final String ERR_REALM_NODATA = "Нет данных в БД Realm!";


    @NonNull  private final GitHubService restApi;
    @NonNull  private final Scheduler dbScheduler;
    @Nullable private Realm database;



    //  Constructor.
    public RepoModelImpl(
            @NonNull GitHubService restApi,
            @NonNull Scheduler dbScheduler) {

        this.restApi = restApi;
        this.dbScheduler = dbScheduler;
    }


    //  Открытие БД.
    private void openDb() {
        database = Realm.getDefaultInstance();
    }


    //  Закрытие БД.
    private void closeDb() {
        if (database == null) return;
        database.close();
        database = null;
    }


    //  Проверка состояния сессии Realm.
    private boolean isDbOpened() {

        return database != null && !database.isClosed();
    }


    //  Загрузка Мастер данных - список public репозиториев.
    //  Отображение данных в Мастер просмотре.

    //  Прежде, чем получать откуда-либо какие-либо данные, мы должны подписаться на этот поток данных
    //  и отписаться, когда работа завершена. Подписаться на этот поток данных можно лишь один раз!
    //  1) OLD Управляем ресурсом Realm (типа открытие/закрытие соединения с БД).
    //     NEW Теперь с Dagger 2 экземпляр Realm на уровне приложения.
    //  2) Получаем Observable потока данных из БД Realm.
    @NonNull
    @Override
    public Observable<? extends List<GitHubRepoMaster>> lifecycleRealm() {

        return Observable.defer(() -> {

            //  Проверяем состояние соединения с БД.
            if (isDbOpened()) {
                throw new IllegalStateException(ERR_REALM_OPENED);
            }

            //  Открываем соединение с БД.
            openDb();

            //  Отложенный поток данных из БД.
            return loadFromRealm();

        }).doOnDispose(() -> {

            Log.d(LOGCAT_TAG, "lifecycleRealm.doOnDispose() !!!");

            //  Проверяем состояние соединения с БД.
            if (!isDbOpened()) {
                throw new IllegalStateException(ERR_REALM_CLOSED);
            }

            //  Закрываем соединение с БД.
            closeDb();
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
            if (!isDbOpened()) {
                throw new IllegalStateException(ERR_REALM_CLOSED);
            }

            //  Формируем запрос к БД.
            return database.where(GitHubRepoMaster.class)
                    //.limit(10)                              //  А можно на этом этапе отсеять!
                    .sort("owner.ownerName")
                    .findAllAsync()                         //  Возвращает тип <? extends List<>>
                    .asFlowable()                           //  В Realm есть только этот каст! :(
                    .filter(RealmResults::isLoaded)         //  TODO Разобраться!
                    .toObservable();

        }).subscribeOn(dbScheduler);                     //  UI поток!
    }


    //  Формирует поток данных из внешнего источника и записывает в БД, если этих данных там еще нет.
    @NonNull
    @Override
    public Observable<? extends List<GitHubRepoMaster>> loadFromInet() {

        return restApi.getRepos()
                .observeOn(dbScheduler)                  //  UI поток!
                .flatMapIterable(list -> list)              //  Метода flatMapIterable() нет в Single<>!
                .take(10)                                   //  TEST!
                .toList()
                .doOnSuccess(repos -> {
                    Log.d(LOGCAT_TAG, "loadFromInet.doOnNext(): " + repos.size());

                    //  Проверяем состояние соединения с БД.
                    if (!isDbOpened()) {
                        throw new IllegalStateException(ERR_REALM_CLOSED);
                    }

                    //  Выполняем транзакцию БД в асинхронном режиме.
                    database.executeTransactionAsync( r -> {

                        //  Вставка списка объектов в БД.
                        //  Теперь данные всегда обновляются => приходят во VIEW!
                        r.insertOrUpdate(repos);

                    });
                })
                .toObservable();
    }



    //  Загрузка Детальных данных выбранного репозитория.
    //  Отображение данных в Детальном просмотре.
    @NonNull
    @Override
    public Single<GitHubRepoDetail> getRepoDetail(String ownerName, String repoName) {

        return restApi.getRepoDetail(ownerName, repoName).observeOn(dbScheduler);
    }


    //  Выборка репозитория по ID из БД Realm.
    //  Для повторного отображения Мастер данных в Детальном просмотре.
    @NonNull
    @Override
    public GitHubRepoMaster getRepoMasterById(int repoId) {

        //  Проверяем состояние соединения с БД.
        if (!isDbOpened()) {
            throw new IllegalStateException(ERR_REALM_CLOSED);
        }

        //  Получаем managed объект.
        GitHubRepoMaster repo = database.where(GitHubRepoMaster.class)
                .equalTo("repoId", repoId).findFirst();

        if (repo == null) {
            throw new IllegalStateException(ERR_REALM_NODATA);
        }

        //  Возвращаем unmanaged копию объекта.
        return database.copyFromRealm(repo);
    }
}
