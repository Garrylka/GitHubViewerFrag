package ru.roskvartal.garry.githubviewerfrag.model.api;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import ru.roskvartal.garry.githubviewerfrag.model.entity.GitHubRepoDetail;
import ru.roskvartal.garry.githubviewerfrag.model.entity.GitHubRepoMaster;


//  Переход на Retrofit/Gson.
public interface GitHubService {

    String API_ENDPOINT = "https://api.github.com";

    @Headers({
            "Accept: application/vnd.github.v3+json",
            "User-Agent: GitHubViewer v1.0 (Retrofit)"
    })
    @GET("repositories")
    Single<List<GitHubRepoMaster>> getRepos();


    @Headers({
            "Accept: application/vnd.github.v3+json",
            "User-Agent: GitHubViewer v1.0 (Retrofit)"
    })
    @GET("repos/{owner}/{repo}")
    Single<GitHubRepoDetail> getRepo(@Path("owner") String ownerName, @Path("repo") String repoName);
}
