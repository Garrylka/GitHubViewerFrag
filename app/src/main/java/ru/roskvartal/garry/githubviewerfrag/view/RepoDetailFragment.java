package ru.roskvartal.garry.githubviewerfrag.view;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.roskvartal.garry.githubviewerfrag.R;
import ru.roskvartal.garry.githubviewerfrag.entity.GitHubRepo;


/**
 *  Фрагмент RepoDetailFragment, в котором спрятан Detail GUI, взаимодействует с DetailActivity.
 *  + Взаимодействует с вложенным фрагментом RepoMainInfoFragment, который повторно использует макет
 *   от элемента списка row_list_repo с основной информацией о репозитории.
 */
public class RepoDetailFragment extends Fragment {

    private static final String LOGCAT_TAG  = "DETAIL";                         //  DEBUG
    public  static final String ARG_REPO_ID = "REPO_ID";                        //  Для Интента и сохранения состояния.

    private int repoID;                                                         //  ID передается из активности.


    //  Метод для установки нового значения repoId, вызывается из DetailActivity.
    public void setRepoId(int repoID) {
        this.repoID = repoID;
    }


    public RepoDetailFragment() {
        // Required empty public constructor.
    }


    //  Используется для инициализации фрагмента.
    //  ACHTUNG!!! Т.к. инициализация фрагмента RepoDetailFragment ЗАВИСИТ от параметра repoId и
    //  метод setCategoryId() вызывается владельцем Фрагмента только после создания фрагмента,
    //  то всю инициализацию надо выполнить в обработчике onStart()!
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {

        //  Восстановление данных при повороте экрана.
        if (savedState != null) {
            repoID = savedState.getInt(ARG_REPO_ID, 0);
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repo_detail, container, false);
    }


    //  Вызывается после onCreate() активности и перед тем, как фрагмент становится видимым.
    //  Параметр repoId уже установлен методом setRepoId() в обработчике onCreate() активности!
    @Override
    public void onStart() {
        super.onStart();

        //  Получение корневого объекта View фрагмента. Теперь можно использовать метод findViewById().
        View rootView = getView();

        //  Repo Language
        TextView repoLang = rootView.findViewById(R.id.textRepoLang);
        repoLang.setText(GitHubRepo.repos[repoID].getMainLang());

        //  Repo Stars Count
        TextView repoStars = rootView.findViewById(R.id.textRepoStars);
        repoStars.setText(String.valueOf(GitHubRepo.repos[repoID].getStarsCount()));

        //  Repo Watchers Count
        TextView repoWatchs = rootView.findViewById(R.id.textRepoWatchers);
        repoWatchs.setText(String.valueOf(GitHubRepo.repos[repoID].getWatchersCount()));

        //  Repo Forks Count
        TextView repoForks = rootView.findViewById(R.id.textRepoForks);
        repoForks.setText(String.valueOf(GitHubRepo.repos[repoID].getForksCount()));

        //  Repo Issues Count
        TextView repoIssues = rootView.findViewById(R.id.textRepoIssues);
        repoIssues.setText(String.valueOf(GitHubRepo.repos[repoID].getIssuesCount()));
    }


    //  Сохранение состояния Фрагмента перед уничтожением.
    @Override
    public void onSaveInstanceState(Bundle saveState) {
        saveState.putInt(ARG_REPO_ID, repoID);
        super.onSaveInstanceState(saveState);
    }
}
