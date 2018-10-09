package ru.roskvartal.garry.githubviewerfrag.model.entity;


import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;


/**
 *  Переход на Retrofit/Gson.
 *  Основные реквизиты Owner.
 *  Поле owner в классе GitHubRepoMaster.
 *  Требуется для правильной десериализации Json данных с сервера GitHub.
 */
public class GitHubRepoOwner {

    @SerializedName("login")
    private String ownerName;           //  Имя владельца (owner.login).

    @SerializedName("avatar_url")
    private String ownerAvatarUrl;      //  Аватар владельца (owner.avatar_url).


    //  Getters.
    @Nullable
    public String getOwnerName() {
        return ownerName;
    }

    @Nullable
    public String getOwnerAvatarUrl() {
        return ownerAvatarUrl;
    }
}
