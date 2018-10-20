package ru.roskvartal.garry.githubviewerfrag.model.entity;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;


/**
 *  Переход на Retrofit/Gson.
 *  Детальные реквизиты репозитория (@GET("repos/{owner}/{repo}")).
 */
public class GitHubRepoDetail {

    private static final String EMPTY = "";

    @SerializedName("id")
    private int    repoId;              //  ID репозитория (id).

    @SerializedName("language")
    private String mainLang;            //  Основной язык кода (language).

    @SerializedName("stargazers_count")
    private int    starsCount;          //  Количество звезд (stargazers_count).

    @SerializedName("watchers_count")
    private int    watchersCount;       //  Количество следящих (watchers_count).

    @SerializedName("forks_count")
    private int    forksCount;          //  Количество ответвлений (forks_count).

    @SerializedName("open_issues_count")
    private int    issuesCount;         //  Количество открытых заявок (open_issues_count).


    //  Getters.
    public int getRepoId() {
        return repoId;
    }

    @NonNull
    public String getMainLang() {
        return mainLang != null ? mainLang : EMPTY;
    }

    public int getStarsCount() {
        return starsCount >= 0 ? starsCount : 0;
    }

    public int getWatchersCount() {
        return watchersCount >= 0 ? watchersCount : 0;
    }

    public int getForksCount() {
        return forksCount >= 0 ? forksCount : 0;
    }

    public int getIssuesCount() {
        return issuesCount >= 0 ? issuesCount : 0;
    }
}
