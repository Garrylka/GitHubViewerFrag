package ru.roskvartal.garry.githubviewerfrag.view;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import ru.roskvartal.garry.githubviewerfrag.R;
import ru.roskvartal.garry.githubviewerfrag.model.entity.GitHubRepoMaster;


/**
 *  Переход на RxJava 2 и List<GitHubRepo>.
 *  Собственный класс Адаптера для RecyclerView.
 *  Переход на ButterKnife.
 */
public class RepoRecyclerViewAdapter extends RecyclerView.Adapter<RepoRecyclerViewAdapter.RepoViewHolder> {

    //  Ссылка на данные.
    private List<GitHubRepoMaster> repos;

    //  Слушатель клика на элементе списка.
    private OnItemClickListener listener;



    public RepoRecyclerViewAdapter() {
        repos = new ArrayList<>();
    }


    //  Интерфейс с методом обратного вызова для обработки клика на элементе списка.
    public interface OnItemClickListener {

        void onItemClick(View itemView, int position);
    }


    //  Установка слушателя.
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    //  OLD: Установка ссылки на данные.
    //public void setRepos(GitHubRepo[] data) {
    //    repos = data;
    //}


    //  Rx: Добавление в список 1 элемента данных.
    public void addRepo(GitHubRepoMaster data) {
        repos.add(data);
    }


    //  Rx: Добавление в список новой порции данных.
    public void addRepos(List<GitHubRepoMaster> data) {
        repos.addAll(data);
    }


    //  Для перехода на Mosby MVP LCE ViewState.
    public List<GitHubRepoMaster> getRepos() {
        return repos;
    }


    //  Предоставляет ссылки ко всем view элемента списка (row).
    public class RepoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //  ViewHolder должен содержать ссылки на все View элемента списка (row).

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


        public RepoViewHolder(View rootView) {
            super(rootView);

            rootView.setOnClickListener(RepoViewHolder.this);

            //  ButterKnife
            ButterKnife.bind(RepoViewHolder.this, rootView);

            //  Теперь привязка View делается через ButterKnife @BindView выше!
            //  User Avatar
            //userAvatar = rootView.findViewById(R.id.imgUserAvatar);
            //...
        }


        //  Обработчик клика на элементе списка.
        @Override
        public void onClick(View view) {
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(view, position);
                }
            }
        }
    }


    //  Создание новых ViewHolder (вызывается layout manager-ом).
    @NonNull
    @Override
    public RepoRecyclerViewAdapter.RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        //  Надуваем наш макет элемента списка (row).
        View repoView = inflater.inflate(R.layout.row_list_repo, parent, false);

        //  Возвращаем новый экземпляр ViewHolder.
        return new RepoViewHolder(repoView);
    }


    //  Заполнение элемента списка данными из модели через ViewHolder (вызывается layout manager-ом).
    @Override
    public void onBindViewHolder(@NonNull RepoRecyclerViewAdapter.RepoViewHolder viewHolder, int position) {

        GitHubRepoMaster repo = repos.get(position);

        //  User Avatar
        //viewHolder.userAvatar.setImageResource(repo.getOwnerAvatarId());
        //  User Name
        viewHolder.userName.setText(repo.getOwnerName());
        //  Repo Name
        viewHolder.repoName.setText(repo.getRepoName());
        //  Repo Desc
        viewHolder.repoDesc.setText(repo.getRepoDesc());
        //  Repo Url
        viewHolder.repoUrl.setText(repo.getRepoUrl());
    }


    //  Возвращает размер нашего набора данных (вызывается layout manager-ом).
    @Override
    public int getItemCount() {
        return (repos == null ? 0 : repos.size());
    }

}
