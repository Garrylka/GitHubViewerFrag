package ru.roskvartal.garry.githubviewerfrag;


import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 *  Для инициализации синглтонов (например, Realm).
 *  В файле манифеста добавить строку в разделе application:
 *  <application
 *         android:name=".MyApplication"
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //  Инициализация Realm.
        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("github_repos_realm.realm")
                .build();
        Realm.deleteRealm(config);                  //  Удаление БД при перезапусках приложения.
        Realm.setDefaultConfiguration(config);      //  Установка новой конфигурации по умолчанию.
    }
}
