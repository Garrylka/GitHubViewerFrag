package ru.roskvartal.garry.githubviewerfrag.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ru.roskvartal.garry.githubviewerfrag.R;
import ru.roskvartal.garry.githubviewerfrag.entity.GitHubRepo;


/**
 *  Собственный класс Адаптера для RecyclerView.
 */
public class RepoRecyclerViewAdapter extends RecyclerView.Adapter<RepoRecyclerViewAdapter.RepoViewHolder> {

    //  Ссылка на данные.
    private GitHubRepo[] repos;

    //  Слушатель клика на элементе списка.
    private OnItemClickListener listener;



    //  Интерфейс с методом обратного вызова для обработки клика на элементе списка.
    public interface OnItemClickListener {

        void onItemClick(View itemView, int position);
    }


    //  Установка слушателя.
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    //  Установка ссылки на данные.
    public void setRepos(GitHubRepo[] repos) {
        this.repos = repos;
    }


    //  Для перехода на Mosby MVP LCE ViewState.
    public GitHubRepo[] getRepos() {
        return repos;
    }


    //  Предоставляет ссылки ко всем view элемента списка (row).
    public class RepoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //  ViewHolder должен содержать ссылки на все View элемента списка (row).

        //  User Avatar
        ImageView userAvatar;
        //  User Name
        TextView userName;
        //  Repo Name
        TextView repoName;
        //  Repo Desc
        TextView repoDesc;
        //  Repo Url
        TextView repoUrl;


        public RepoViewHolder(View rootView) {
            super(rootView);

            rootView.setOnClickListener(RepoViewHolder.this);

            //  User Avatar
            userAvatar = rootView.findViewById(R.id.imgUserAvatar);
            //  User Name
            userName = rootView.findViewById(R.id.textUserName);
            //  Repo Name
            repoName = rootView.findViewById(R.id.textRepoName);
            //  Repo Desc
            repoDesc = rootView.findViewById(R.id.textRepoDesc);
            //  Repo Url
            repoUrl = rootView.findViewById(R.id.textRepoUrl);
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

        //  User Avatar
        viewHolder.userAvatar.setImageResource(repos[position].getOwnerAvatarId());
        //  User Name
        viewHolder.userName.setText(repos[position].getOwnerName());
        //  Repo Name
        viewHolder.repoName.setText(repos[position].getRepoName());
        //  Repo Desc
        viewHolder.repoDesc.setText(repos[position].getRepoDesc());
        //  Repo Url
        viewHolder.repoUrl.setText(repos[position].getRepoUrl());
    }


    //  Возвращает размер нашего набора данных (вызывается layout manager-ом).
    @Override
    public int getItemCount() {
        return (repos == null ? 0 : repos.length);
    }

}
