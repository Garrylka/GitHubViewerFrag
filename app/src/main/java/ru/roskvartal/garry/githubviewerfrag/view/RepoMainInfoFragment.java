package ru.roskvartal.garry.githubviewerfrag.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import ru.roskvartal.garry.githubviewerfrag.MyApplication;
import ru.roskvartal.garry.githubviewerfrag.R;
import ru.roskvartal.garry.githubviewerfrag.model.RepoModel;
import ru.roskvartal.garry.githubviewerfrag.model.entity.GitHubRepoMaster;
import ru.roskvartal.garry.githubviewerfrag.model.image.ImageLoader;


/**
 *  Вложенный фрагмент RepoMainInfoFragment встраивается в макет фрагмента RepoDetailFragment для
 *  вывода основной информации о репозитории с использованием макета от элемента списка row_list_repo.xml.
 *  Повторное использование макета row_list_repo.xml !
 *  Переход на ButterKnife.
 */
public class RepoMainInfoFragment extends Fragment {

    private static final String LOGCAT_TAG  = "DETAIL";                         //  DEBUG

    private int repoId;                                                         //  ID передается от владельца.

    //  ButterKnife.
    //  User Avatar
    @BindView(R.id.imgUserAvatar) ImageView userAvatar;
    //  User Name
    @BindView(R.id.textUserName) TextView userName;
    //  Repo Name
    @BindView(R.id.textRepoName) TextView repoName;
    //  Repo Desc
    @BindView(R.id.textRepoDesc) TextView repoDesc;
    //  Repo Url
    @BindView(R.id.textRepoUrl) TextView repoUrl;


    @Nullable
    private Unbinder unbinder;

    @Nullable
    private RepoModel model;                                                    //  MVP - Model.

    @Nullable
    private ImageLoader<ImageView> imageLoader;                                 //  ImageLoader для загрузки Аватарок.



    //  Метод для установки нового значения repoID, вызывается из DetailActivity.
    public void setRepoId(int repoId) {
        this.repoId = repoId;
    }


    public RepoMainInfoFragment() {
        //  Required empty public constructor
    }


    //  Используется для инициализации фрагмента.
    //  ACHTUNG!!! Т.к. инициализация фрагмента RepoMainInfoFragment ЗАВИСИТ от параметра repoID и
    //  метод setRepoId() вызывается владельцем Фрагмента только после создания фрагмента,
    //  то всю инициализацию надо выполнить в обработчике onStart()!
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedState) {

        //  Восстановление данных при повороте экрана.
        if (savedState != null) {
            repoId = savedState.getInt(RepoDetailFragment.ARG_REPO_ID, 0);
        }

        //  Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.row_list_repo, container, false);

        //  ButterKnife
        unbinder = ButterKnife.bind(this, rootView);

        //  Модель уже инициализирована в Презентере Мастера, а Презентер с сохранением состояния -> БД открыта.
        model = MyApplication.getApp().getAppComponent().getRepoModel();

        //  ImageLoader для загрузки Аватарок.
        imageLoader = MyApplication.getApp().getAppComponent().getImageLoader();

        return rootView;
    }


    //  Вызывается после onCreate() активности и перед тем, как фрагмент становится видимым.
    //  Параметр repoID уже установлен методом setRepoId() в обработчике onStart() фрагмента-владельца!
    @Override
    public void onStart() {
        super.onStart();

        //  Получение корневого объекта View фрагмента. Теперь можно использовать метод findViewById().
        //  ButterKnife - View rootView = getView();

        //  Запрос Мастер данных из локальной БД.
        GitHubRepoMaster repo = model.getRepoMasterById(repoId);

        //  User Avatar
        imageLoader.loadImage(repo.getOwnerAvatarUrl(), userAvatar);
        //  User Name
        userName.setText(repo.getOwnerName());
        //  Repo Name
        repoName.setText(repo.getRepoName());
        //  Repo Desc
        repoDesc.setText(repo.getRepoDesc());
        //  Repo Url
        repoUrl.setText(repo.getRepoUrl());
    }


    //  Сохранение состояния Фрагмента перед уничтожением.
    @Override
    public void onSaveInstanceState(@NonNull Bundle saveState) {
        saveState.putInt(RepoDetailFragment.ARG_REPO_ID, repoId);
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
    }
}
