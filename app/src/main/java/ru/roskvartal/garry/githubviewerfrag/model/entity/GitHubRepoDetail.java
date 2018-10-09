package ru.roskvartal.garry.githubviewerfrag.model.entity;


import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;


/**
 *  Переход на Retrofit/Gson.
 *  Детальные реквизиты репозитория (@GET("repos/{owner}/{repo}")).
 */
public class GitHubRepoDetail {

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


    @Nullable
    public String getMainLang() {
        return mainLang;
    }

    public int getStarsCount() {
        return starsCount;
    }

    public int getWatchersCount() {
        return watchersCount;
    }

    public int getForksCount() {
        return forksCount;
    }

    public int getIssuesCount() {
        return issuesCount;
    }
}
