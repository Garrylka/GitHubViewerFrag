package ru.roskvartal.garry.githubviewerfrag.model.entity;


import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;


/**
 *  Переход на Retrofit/Gson.
 *  Основные реквизиты репозитория (@GET("repositories")).
 */
public class GitHubRepoMaster {

    @SerializedName("id")
    private int    repoId;              //  ID - число (id).

    @SerializedName("name")
    private String repoName;            //  Краткое название репозитория (name).

    @SerializedName("full_name")
    private String repoFullName;        //  Полное название репозитория (full_name).

    @SerializedName("description")
    private String repoDesc;            //  Краткое описание репозитория (description).

    @SerializedName("html_url")
    private String repoUrl;             //  Ссылка на репозиторий (html_url).

    private GitHubRepoOwner owner;      //  Подкласс (owner).


    //  Getters.
    public int getRepoId() {
        return repoId;
    }

    @Nullable
    public String getRepoName() {
        return repoName;
    }

    @Nullable
    public String getRepoFullName() {
        return repoFullName;
    }

    @Nullable
    public String getRepoDesc() {
        return repoDesc;
    }

    @Nullable
    public String getRepoUrl() {
        return repoUrl;
    }

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
}
