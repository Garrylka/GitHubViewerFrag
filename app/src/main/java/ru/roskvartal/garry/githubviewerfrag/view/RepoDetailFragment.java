package ru.roskvartal.garry.githubviewerfrag.view;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import ru.roskvartal.garry.githubviewerfrag.MyApplication;
import ru.roskvartal.garry.githubviewerfrag.R;
import ru.roskvartal.garry.githubviewerfrag.model.RepoModel;
import ru.roskvartal.garry.githubviewerfrag.model.entity.GitHubRepoDetail;
import ru.roskvartal.garry.githubviewerfrag.model.entity.GitHubRepoMaster;


/**
 *  Переход на Retrofit / Picasso / Realm.
 *  Фрагмент RepoDetailFragment, в котором спрятан Detail GUI, взаимодействует с DetailActivity.
 *  + Взаимодействует с вложенным фрагментом RepoMainInfoFragment, который повторно использует макет
 *   от элемента списка row_list_repo с основной информацией о репозитории.
 */
public class RepoDetailFragment extends Fragment {

    private static final String LOGCAT_TAG  = "DETAIL";                         //  DEBUG
    public  static final String ARG_REPO_ID = "REPO_ID";                        //  Для Интента и сохранения состояния.

    private int repoId;                                                         //  ID передается из активности.

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


    @Nullable
    private Unbinder unbinder;

    @Nullable
    private RepoModel model;                                                    //  MVP - Model.

    @Nullable
    private Disposable loading;


    //  Метод для установки нового значения repoId, вызывается из DetailActivity.
    public void setRepoId(int repoId) {
        this.repoId = repoId;
    }


    public RepoDetailFragment() {
        // Required empty public constructor.
    }


    //  Используется для инициализации фрагмента.
    //  ACHTUNG!!! Т.к. инициализация фрагмента RepoDetailFragment ЗАВИСИТ от параметра repoId и
    //  метод setRepoId() вызывается владельцем Фрагмента только после создания фрагмента,
    //  то всю инициализацию надо выполнить в обработчике onStart()!
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedState) {

        //  Восстановление данных при повороте экрана.
        if (savedState != null) {
            repoId = savedState.getInt(ARG_REPO_ID, 0);
        }

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_repo_detail, container, false);

        //  ButterKnife.
        unbinder = ButterKnife.bind(this, rootView);

        //  Модель уже инициализирована в Презентере Мастера -> restApi тоже инициализировано.
        model = MyApplication.getApp().getAppComponent().getRepoModel();

        return rootView;
    }


    //  Вызывается после onCreate() активности и перед тем, как фрагмент становится видимым.
    //  Параметр repoID уже установлен методом setRepoId() в обработчике onCreate() активности!
    //@SuppressLint("CheckResult")
    @Override
    public void onStart() {
        super.onStart();

        //  Получение корневого объекта View фрагмента. Теперь можно использовать метод findViewById().
        //  ButterKnife - View rootView = getView();

        //  Переход на Retrofit / Picasso / Realm.

        //  Запрос Мастер данных из локальной БД.
        GitHubRepoMaster repo = model.getRepoMasterById(repoId);

        //  Запрос Детальных данных из Интернет через restApi по данным Мастера: OwnerName, RepoName.
        loading = model.getRepoDetail(repo.getOwnerName(), repo.getRepoName())
                .subscribeWith(new DisposableSingleObserver<GitHubRepoDetail>() {
                    @Override
                    public void onSuccess(GitHubRepoDetail data) {
                        //  Repo Language
                        repoLang.setText(data.getMainLang());

                        //  Repo Stars Count
                        repoStars.setText(String.valueOf(data.getStarsCount()));

                        //  Repo Watchers Count
                        repoWatchs.setText(String.valueOf(data.getWatchersCount()));

                        //  Repo Forks Count
                        repoForks.setText(String.valueOf(data.getForksCount()));

                        //  Repo Issues Count
                        repoIssues.setText(String.valueOf(data.getIssuesCount()));
                    }

                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


        //  Отображение встроенного СТАТИЧЕСКОГО <fragment> RepoMainInfoFragment для вывода основной
        //  информации о репозитории с использованием макета от элемента списка row_list_repo.xml.
        FragmentManager childFragmentManager = getChildFragmentManager();
        RepoMainInfoFragment fragmentRepoMainInfo =
                (RepoMainInfoFragment) childFragmentManager.findFragmentById(R.id.fragmentRepoMainInfo);

        //  Передаем repoID вложенному фрагменту, он заполнит свои представления правильным контентом.
        if (fragmentRepoMainInfo != null) {
            fragmentRepoMainInfo.setRepoId(repoId);
        }

    }


    //  Сохранение состояния Фрагмента перед уничтожением.
    @Override
    public void onSaveInstanceState(@NonNull Bundle saveState) {
        saveState.putInt(ARG_REPO_ID, repoId);
        super.onSaveInstanceState(saveState);
    }


    //  ButterKnife. Set the views to null.
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }

        unsubscribe(loading);
        loading = null;
    }


    //  Отписка от потока.
    private void unsubscribe(@Nullable Disposable source) {

        if (isSubscribed(source)) {
            source.dispose();
        }
    }


    //  Проверка подписки на поток.
    private boolean isSubscribed(@Nullable Disposable source) {

        return source != null && !source.isDisposed();
    }

}
