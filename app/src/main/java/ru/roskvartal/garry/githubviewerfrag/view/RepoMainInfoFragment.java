package ru.roskvartal.garry.githubviewerfrag.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import ru.roskvartal.garry.githubviewerfrag.R;
import ru.roskvartal.garry.githubviewerfrag.model.entity.GitHubRepo;


/**
 *  Вложенный фрагмент RepoMainInfoFragment встраивается в макет фрагмента RepoDetailFragment для
 *  вывода основной информации о репозитории с использованием макета от элемента списка row_list_repo.xml.
 *  Повторное использование макета row_list_repo.xml !
 *  Переход на ButterKnife.
 */
public class RepoMainInfoFragment extends Fragment {

    private static final String LOGCAT_TAG  = "DETAIL";                         //  DEBUG

    private int repoID;                                                         //  ID передается из владельца.

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

    private Unbinder unbinder;



    //  Метод для установки нового значения repoID, вызывается из DetailActivity.
    public void setRepoId(int repoID) {
        this.repoID = repoID;
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
            repoID = savedState.getInt(RepoDetailFragment.ARG_REPO_ID, 0);
        }

        //  Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.row_list_repo, container, false);

        //  ButterKnife
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }


    //  Вызывается после onCreate() активности и перед тем, как фрагмент становится видимым.
    //  Параметр repoID уже установлен методом setRepoId() в обработчике onStart() фрагмента-владельца!
    @Override
    public void onStart() {
        super.onStart();

        //  Получение корневого объекта View фрагмента. Теперь можно использовать метод findViewById().
        //  ButterKnife - View rootView = getView();

        //  User Avatar
        userAvatar.setImageResource(GitHubRepo.repos[repoID].getOwnerAvatarId());
        //  User Name
        userName.setText(GitHubRepo.repos[repoID].getOwnerName());
        //  Repo Name
        repoName.setText(GitHubRepo.repos[repoID].getRepoName());
        //  Repo Desc
        repoDesc.setText(GitHubRepo.repos[repoID].getRepoDesc());
        //  Repo Url
        repoUrl.setText(GitHubRepo.repos[repoID].getRepoUrl());
    }


    //  Сохранение состояния Фрагмента перед уничтожением.
    @Override
    public void onSaveInstanceState(@NonNull Bundle saveState) {
        saveState.putInt(RepoDetailFragment.ARG_REPO_ID, repoID);
        super.onSaveInstanceState(saveState);
    }


    //  ButterKnife. Set the views to null.
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
