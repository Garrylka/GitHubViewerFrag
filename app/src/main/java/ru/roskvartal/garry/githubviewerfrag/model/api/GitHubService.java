package ru.roskvartal.garry.githubviewerfrag.model.api;


import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

import ru.roskvartal.garry.githubviewerfrag.model.entity.GitHubRepoDetail;
import ru.roskvartal.garry.githubviewerfrag.model.entity.GitHubRepoMaster;


/**
 *  Добавление БД Realm.
 *  Переход на Retrofit/Gson.
 *  Интерфейс для генерации кода Retrofit.
 */
public interface GitHubService {

    String API_ENDPOINT = "https://api.github.com";


    //  Загрузка Мастер данных - список public репозиториев.
    //  Прилетает JSON-массив[100], заказать меньшее количество данных через GitHub API нельзя!
    @Headers({
            "Accept: application/vnd.github.v3+json",
            "User-Agent: GitHubViewer v1.0 (Retrofit)"
    })
    @GET("repositories")
    Flowable<List<GitHubRepoMaster>> getRepos();    //  Можно бы и Single<List<>>, но далее используется flatMapIterable()!


    //  Загрузка Детальных данных выбранного репозитория.
    //  Запрашивается по URL с двумя параметрами: owner и repo (имя владельца репо и имя репо из Мастер данных).
    @Headers({
            "Accept: application/vnd.github.v3+json",
            "User-Agent: GitHubViewer v1.0 (Retrofit)"
    })
    @GET("repos/{owner}/{repo}")
    Single<GitHubRepoDetail> getRepoDetail(@Path("owner") String ownerName, @Path("repo") String repoName);
}
