package ru.roskvartal.garry.githubviewerfrag.model.entity;


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

    @Nullable
    public String getRepoName() {
        return repoName;
    }

    @Nullable
    public String getRepoDesc() {
        return repoDesc;
    }

    @Nullable
    public String getRepoUrl() {
        return repoUrl;
    }


    //  Getters Owner.
    @Nullable
    public String getOwnerName() {
        if (owner == null) return null;
        return owner.getOwnerName();
    }

    @Nullable
    public String getOwnerAvatarUrl() {
        if (owner == null) return null;
        return owner.getOwnerAvatarUrl();
    }


    //  Setters (для обновления в Realm).
    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public void setRepoDesc(String repoDesc) {
        this.repoDesc = repoDesc;
    }

    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }

}
