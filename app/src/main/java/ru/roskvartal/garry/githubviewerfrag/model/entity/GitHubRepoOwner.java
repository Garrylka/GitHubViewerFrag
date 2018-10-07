package ru.roskvartal.garry.githubviewerfrag.model.entity;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;


//  Переход на Retrofit/Gson.
//  Основные реквизиты Owner.
public class GitHubRepoOwner {

    @SerializedName("login")
    private String ownerName;           //  Имя владельца (owner.login).

    @SerializedName("avatar_url")
    private String ownerAvatarUrl;      //  Аватар владельца (owner.avatar_url).

    @Nullable
    public String getOwnerName() {
        return ownerName;
    }

    @Nullable
    public String getOwnerAvatarUrl() {
        return ownerAvatarUrl;
    }
}
