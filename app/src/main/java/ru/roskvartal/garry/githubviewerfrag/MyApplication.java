package ru.roskvartal.garry.githubviewerfrag;


import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import ru.roskvartal.garry.githubviewerfrag.di.AppComponent;
import ru.roskvartal.garry.githubviewerfrag.di.DaggerAppComponent;


/**
 *  Инициализация Dagger 2 компонента приложения.
 *  В файле манифеста добавить строку в разделе application:
 *  <application
 *         android:name=".MyApplication"
 */
public class MyApplication extends Application {

    private static final String DATABASE_NAME = "github_repos_realm.realm";

    private static MyApplication instance;

    private AppComponent component;


    public static MyApplication getApp() {

        return instance;
    }


    //  Инициализация Realm.
    private void initRealm() {

        Realm.init(this);

        //  Установка новой конфигурации БД.
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(DATABASE_NAME)
                .build();

        //  Удаление БД при перезапусках приложения.
        //Realm.deleteRealm(config);

        //  Установка config конфигурацией по умолчанию.
        Realm.setDefaultConfiguration(config);
    }


    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        initRealm();

        component = DaggerAppComponent.builder().build();
    }


    public AppComponent getAppComponent() {

        return component;
    }

}
