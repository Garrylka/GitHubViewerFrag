package ru.roskvartal.garry.githubviewerfrag.view;

import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;
import java.util.List;
import ru.roskvartal.garry.githubviewerfrag.entity.GitHubRepo;


//  Переход на RxJava 2 и List<GitHubRepo>.
public interface ReposView extends MvpLceView<List<GitHubRepo>> {

    void clearViewData();

    int getDataCount();
}
