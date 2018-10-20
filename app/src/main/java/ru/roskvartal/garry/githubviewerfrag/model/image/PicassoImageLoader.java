package ru.roskvartal.garry.githubviewerfrag.model.image;


import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import ru.roskvartal.garry.githubviewerfrag.R;


/**
 *  Подключение библиотеки Picasso для загрузки изображений из Интернет.
 */
public class PicassoImageLoader implements ImageLoader<ImageView> {

    @NonNull
    private Transformation transformation;


    public PicassoImageLoader(@NonNull Transformation transformation) {

        this.transformation = transformation;
    }


    @Override
    public void loadImage(@NonNull String url, @NonNull ImageView view) {
        //  Singleton.
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.avatar_load)
                .error(R.drawable.avatar_error)
                .resizeDimen(R.dimen.avatar_img_size, R.dimen.avatar_img_size)
                .transform(transformation)
                .into(view);
    }
}
