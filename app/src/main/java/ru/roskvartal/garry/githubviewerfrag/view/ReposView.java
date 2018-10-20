package ru.roskvartal.garry.githubviewerfrag.view;


import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

import java.util.List;

import ru.roskvartal.garry.githubviewerfrag.model.entity.GitHubRepoMaster;


/**
 *  Подключение в проетк библиотеки Picasso для загрузки изображений из Интернет.
 *  Переход на Retrofit/Gson.
 *  Переход на ButterKnife.
 *  Переход на RxJava 2 и List<T>.
 */
public interface ReposView extends MvpLceView<List<GitHubRepoMaster>> {

}
