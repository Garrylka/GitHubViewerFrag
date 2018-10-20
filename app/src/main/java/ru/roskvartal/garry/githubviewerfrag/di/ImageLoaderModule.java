package ru.roskvartal.garry.githubviewerfrag.di;


import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.Transformation;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import ru.roskvartal.garry.githubviewerfrag.model.image.ImageCircleTransform;
import ru.roskvartal.garry.githubviewerfrag.model.image.ImageLoader;
import ru.roskvartal.garry.githubviewerfrag.model.image.PicassoImageLoader;


/**
 *  Подключил Dagger 2 для реализации паттерна DI.
 */
@Module
public class ImageLoaderModule {

    @Singleton
    @Provides
    @NonNull
    public Transformation provideImageTransformation() {

        return new ImageCircleTransform();
    }


    @Singleton
    @Provides
    @NonNull
    public ImageLoader<ImageView> provideImageLoader(@NonNull Transformation transformation) {

        return new PicassoImageLoader(transformation);
    }

}
