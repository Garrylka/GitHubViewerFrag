package ru.roskvartal.garry.githubviewerfrag.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
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

    //  ButterKnife.
    //  Repo Language
    @BindView(R.id.textRepoLang) TextView repoLang;
    //  Repo Stars Count
    @BindView(R.id.textRepoStars) TextView repoStars;
    //  Repo Watchers Count
    @BindView(R.id.textRepoWatchers) TextView repoWatchs;
    //  Repo Forks Count
    @BindView(R.id.textRepoForks) TextView repoForks;
    //  Repo Issues Count
    @BindView(R.id.textRepoIssues) TextView repoIssues;

    private Unbinder unbinder;



    //  Метод для установки нового значения repoID, вызывается из DetailActivity.
    public void setRepoId(int repoID) {
        this.repoID = repoID;
    }


    public RepoDetailFragment() {
        // Required empty public constructor.
    }


    //  Используется для инициализации фрагмента.
    //  ACHTUNG!!! Т.к. инициализация фрагмента RepoDetailFragment ЗАВИСИТ от параметра repoID и
    //  метод setRepoId() вызывается владельцем Фрагмента только после создания фрагмента,
    //  то всю инициализацию надо выполнить в обработчике onStart()!
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedState) {

        //  Восстановление данных при повороте экрана.
        if (savedState != null) {
            repoID = savedState.getInt(ARG_REPO_ID, 0);
        }

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_repo_detail, container, false);

        //  ButterKnife.
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }


    //  Вызывается после onCreate() активности и перед тем, как фрагмент становится видимым.
    //  Параметр repoID уже установлен методом setRepoId() в обработчике onCreate() активности!
    @Override
    public void onStart() {
        super.onStart();

        //  Получение корневого объекта View фрагмента. Теперь можно использовать метод findViewById().
        //  ButterKnife - View rootView = getView();

        //  Repo Language
        //TextView repoLang = rootView.findViewById(R.id.textRepoLang);
        repoLang.setText(GitHubRepo.repos[repoID].getMainLang());

        //  Repo Stars Count
        //TextView repoStars = rootView.findViewById(R.id.textRepoStars);
        repoStars.setText(String.valueOf(GitHubRepo.repos[repoID].getStarsCount()));

        //  Repo Watchers Count
        //TextView repoWatchs = rootView.findViewById(R.id.textRepoWatchers);
        repoWatchs.setText(String.valueOf(GitHubRepo.repos[repoID].getWatchersCount()));

        //  Repo Forks Count
        //TextView repoForks = rootView.findViewById(R.id.textRepoForks);
        repoForks.setText(String.valueOf(GitHubRepo.repos[repoID].getForksCount()));

        //  Repo Issues Count
        //TextView repoIssues = rootView.findViewById(R.id.textRepoIssues);
        repoIssues.setText(String.valueOf(GitHubRepo.repos[repoID].getIssuesCount()));


        //  Отображение встроенного СТАТИЧЕСКОГО <fragment> RepoMainInfoFragment для вывода основной
        //  информации о репозитории с использованием макета от элемента списка row_list_repo.xml.
        FragmentManager childFragmentManager = getChildFragmentManager();
        RepoMainInfoFragment fragmentRepoMainInfo =
                (RepoMainInfoFragment) childFragmentManager.findFragmentById(R.id.fragmentRepoMainInfo);

        //  Передаем repoID вложенному фрагменту, он заполнит свои представления правильным контентом.
        if (fragmentRepoMainInfo != null) {
            fragmentRepoMainInfo.setRepoId(repoID);
        }

    }


    //  Сохранение состояния Фрагмента перед уничтожением.
    @Override
    public void onSaveInstanceState(@NonNull Bundle saveState) {
        saveState.putInt(ARG_REPO_ID, repoID);
        super.onSaveInstanceState(saveState);
    }


    //  ButterKnife. Set the views to null.
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
