package ru.roskvartal.garry.githubviewerfrag.model.image;


import android.support.annotation.NonNull;


/**
 *  Подключение в проетк библиотеки Picasso для загрузки изображений из Интернет.
 */
public interface ImageLoader<T> {

    void loadImage(@NonNull String url, @NonNull T view);
}
