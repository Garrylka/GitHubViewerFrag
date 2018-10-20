package ru.roskvartal.garry.githubviewerfrag.model.entity;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


/**
 *  Добавление БД Realm.
 *  Переход на Retrofit/Gson.
 *  Мастер данные - основные реквизиты репозитория + Данные владельца Owner.
 *  Запрашиваются через GitHub API при помощи Retrofit запросом @GET("repositories").
 */
public class GitHubRepoMaster extends RealmObject {

    private static final String EMPTY = "";

    @PrimaryKey                         //  Требуется для правильной вставки в БД Realm без дубликатов.
    @SerializedName("id")
    private int    repoId;              //  ID репозитория (id).

    @SerializedName("name")
    private String repoName;            //  Краткое название репозитория (name).

    @SerializedName("description")
    private String repoDesc;            //  Краткое описание репозитория (description).

    @SerializedName("html_url")
    private String repoUrl;             //  Ссылка на репозиторий (html_url).

    private GitHubRepoOwner owner;      //  Подкласс (owner).


    //  Getters Repo.
    public int getRepoId() {
        return repoId;
    }

    @NonNull
    public String getRepoName() {
        return repoName != null ? repoName : EMPTY;
    }

    @NonNull
    public String getRepoDesc() {
        return repoDesc != null ? repoDesc : EMPTY;
    }

    @NonNull
    public String getRepoUrl() {
        return repoUrl != null ? repoUrl : EMPTY;
    }


    //  Getters Owner.
    @NonNull
    public String getOwnerName() {
        return owner != null ? owner.getOwnerName() : EMPTY;
    }

    @NonNull
    public String getOwnerAvatarUrl() {
        return owner != null ? owner.getOwnerAvatarUrl() : EMPTY;
    }
}
