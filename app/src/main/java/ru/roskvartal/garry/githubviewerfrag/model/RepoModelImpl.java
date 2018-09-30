package ru.roskvartal.garry.githubviewerfrag.model;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import ru.roskvartal.garry.githubviewerfrag.entity.GitHubRepo;


//  Переход на Mosby MVP LCE.
public class RepoModelImpl implements RepoModel {

    @Override
    public GitHubRepo[] getRepos() {
        return GitHubRepo.repos;
    }


    @Override
    public GitHubRepo getRepoByPos(int position) {
        return GitHubRepo.repos[position];
    }


    @Nullable
    @Override
    public GitHubRepo getRepoById(int repoId) {
        for (GitHubRepo r: GitHubRepo.repos) {
            if (r.getRepoId() == repoId) {
                return r;
            }
        }
        return null;
    }


    @Override
    public int getReposCount() {
        return GitHubRepo.repos.length;
    }


    //  TEST Тестирование ProgressBar при помощи эмуляции задержки загрузки данных.
    @SuppressLint("StaticFieldLeak")
    @Override
    public void getReposDefer(final MyTestAction<GitHubRepo[]> onNext) {

        new AsyncTask<Void, Void, GitHubRepo[]>() {
            @Override
            protected GitHubRepo[] doInBackground(Void ... params) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return getRepos();
            }
            @Override
            protected void onPostExecute (GitHubRepo[] data) {
                onNext.call(data);
            }
        }.execute ();
    }

}
