package ru.roskvartal.garry.githubviewerfrag.model.api;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


/**
 *  Подключение в проетк библиотеки Picasso для загрузки изображений из Интернет.
 */
public interface ImageLoader<T> {

    void loadImage(@Nullable String url, @NonNull T view);
}
