package ru.roskvartal.garry.githubviewerfrag.view;

import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

import java.util.List;

import ru.roskvartal.garry.githubviewerfrag.model.entity.GitHubRepo;
import ru.roskvartal.garry.githubviewerfrag.model.entity.GitHubRepoMaster;


//  Переход на Retrofit/Gson.
//  Переход на RxJava 2 и List<GitHubRepo>.
public interface ReposView extends MvpLceView<List<GitHubRepoMaster>> {

    void clearViewData();

    int getDataCount();
}
