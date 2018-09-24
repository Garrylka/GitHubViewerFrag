package ru.roskvartal.garry.githubviewerfrag.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.roskvartal.garry.githubviewerfrag.R;
import ru.roskvartal.garry.githubviewerfrag.entity.GitHubRepo;


/**
 *  Вложенный фрагмент RepoMainInfoFragment встраивается в макет фрагмента RepoDetailFragment для
 *  вывода основной информации о репозитории с использованием макета от элемента списка row_list_repo.xml.
 *  Повторное использование макета row_list_repo.xml !
 */
public class RepoMainInfoFragment extends Fragment {

    private static final String LOGCAT_TAG  = "DETAIL";                         //  DEBUG

    private int repoID;                                                         //  ID передается из владельца.


    //  Метод для установки нового значения repoID, вызывается из DetailActivity.
    public void setRepoId(int repoID) {
        this.repoID = repoID;
    }


    public RepoMainInfoFragment() {
        // Required empty public constructor
    }


    //  Используется для инициализации фрагмента.
    //  ACHTUNG!!! Т.к. инициализация фрагмента RepoMainInfoFragment ЗАВИСИТ от параметра repoID и
    //  метод setRepoId() вызывается владельцем Фрагмента только после создания фрагмента,
    //  то всю инициализацию надо выполнить в обработчике onStart()!
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {

        //  Восстановление данных при повороте экрана.
        if (savedState != null) {
            repoID = savedState.getInt(RepoDetailFragment.ARG_REPO_ID, 0);
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.row_list_repo, container, false);
    }


    //  Вызывается после onCreate() активности и перед тем, как фрагмент становится видимым.
    //  Параметр repoID уже установлен методом setRepoId() в обработчике onStart() фрагмента-владельца!
    @Override
    public void onStart() {
        super.onStart();

        //  Получение корневого объекта View фрагмента. Теперь можно использовать метод findViewById().
        View rootView = getView();

        //  User Avatar
        ImageView userAvatar = rootView.findViewById(R.id.imgUserAvatar);
        userAvatar.setImageResource(GitHubRepo.repos[repoID].getOwnerAvatarId());

        //  User Name
        TextView userName = rootView.findViewById(R.id.textUserName);
        userName.setText(GitHubRepo.repos[repoID].getOwnerName());

        //  Repo Name
        TextView repoName = rootView.findViewById(R.id.textRepoName);
        repoName.setText(GitHubRepo.repos[repoID].getRepoName());

        //  Repo Desc
        TextView repoDesc = rootView.findViewById(R.id.textRepoDesc);
        repoDesc.setText(GitHubRepo.repos[repoID].getRepoDesc());

        //  Repo Url
        TextView repoUrl = rootView.findViewById(R.id.textRepoUrl);
        repoUrl.setText(GitHubRepo.repos[repoID].getRepoUrl());
    }


    //  Сохранение состояния Фрагмента перед уничтожением.
    @Override
    public void onSaveInstanceState(Bundle saveState) {
        saveState.putInt(RepoDetailFragment.ARG_REPO_ID, repoID);
        super.onSaveInstanceState(saveState);
    }
}
