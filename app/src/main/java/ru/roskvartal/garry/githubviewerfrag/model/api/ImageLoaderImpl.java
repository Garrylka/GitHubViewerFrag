package ru.roskvartal.garry.githubviewerfrag.model.api;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ru.roskvartal.garry.githubviewerfrag.R;


/**
 *  Подключение в проетк библиотеки Picasso для загрузки изображений из Интернет.
 */
public class ImageLoaderImpl implements ImageLoader<ImageView> {

    @Override
    public void loadImage(@Nullable String url, @NonNull ImageView view) {
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.avatar_load)
                .error(R.drawable.avatar_error)
                .resizeDimen(R.dimen.avatar_img_size, R.dimen.avatar_img_size)
                .transform(new ImageCircleTransform())
                .into(view);
    }
}
