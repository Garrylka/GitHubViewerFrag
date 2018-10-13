package ru.roskvartal.garry.githubviewerfrag.model.entity;


import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


/**
 *  Добавление БД Realm.
 *  Переход на Retrofit/Gson.
 *  Мастер данные - основные реквизиты Owner (мембер owner в классе GitHubRepoMaster).
 *  Требуется для правильной автоматической десериализации Json данных о public репозиториях с сервера GitHub.
 */
public class GitHubRepoOwner extends RealmObject {

    @PrimaryKey                         //  Требуется для правильной вставки в БД Realm без дубликатов.
    @SerializedName("id")
    private int    ownerId;             //  ID владельца (id).

    @SerializedName("login")
    private String ownerName;           //  Имя владельца (owner.login).

    @SerializedName("avatar_url")
    private String ownerAvatarUrl;      //  Аватар владельца (owner.avatar_url).


    //  Getters.
    public int getOwnerId() {
        return ownerId;
    }

    @Nullable
    public String getOwnerName() {
        return ownerName;
    }

    @Nullable
    public String getOwnerAvatarUrl() {
        return ownerAvatarUrl;
    }
}
