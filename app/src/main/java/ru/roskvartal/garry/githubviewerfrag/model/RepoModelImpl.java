package ru.roskvartal.garry.githubviewerfrag.model;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.Random;

import ru.roskvartal.garry.githubviewerfrag.entity.GitHubRepo;


//  Переход на Mosby MVP LCE.
public class RepoModelImpl implements RepoModel {

    //  TEST: Эмуляция ошибки при загрузке данных.
    private Random random = new Random();
    private Throwable error = null;


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


    //  TEST Тестирование:
    //  1) ProgressBar при помощи эмуляции задержки загрузки данных.
    @SuppressLint("StaticFieldLeak")
    @Override
    public void getReposDefer(final MyTestAction<GitHubRepo[]> onNext) {

        new AsyncTask<Void, Void, GitHubRepo[]>() {

            @Override
            protected GitHubRepo[] doInBackground(Void ... params) {
                try {
                    Thread.sleep(2000);
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

    //  2) Эмуляция задержки и ошибки при загрузке данных.
    @SuppressLint("StaticFieldLeak")
    @Override
    public void getReposDeferError(final MyTestAction<GitHubRepo[]> onNext) {

        new AsyncTask<Void, Void, GitHubRepo[]>() {

            @Override
            protected GitHubRepo[] doInBackground(Void ... params) {
                try {
                    Thread.sleep(2000);

                    //  TEST: Эмуляция ошибки при загрузке данных.
                    if (random.nextBoolean()) {
                        throw new IOException();
                    }
                } catch (InterruptedException | IOException e) {
                    //e.printStackTrace();
                    //  TEST: Эмуляция ошибки при загрузке данных.
                    error = e;
                    return null;
                }
                return getRepos();
            }

            @Override
            protected void onPostExecute (GitHubRepo[] data) {
                onNext.call(data);
            }

        }.execute();
    }


    @Override
    public Throwable getError() {
        return error;
    }


    //  3) Другой вариант эмуляции задержки и ошибки при загрузке данных.
    //  Используется два отдельных Action: для получения данных, при возникновении ошибки.
    @SuppressLint("StaticFieldLeak")
    @Override
    public void getReposDeferError2(
            final MyTestAction<GitHubRepo[]> onNext, final MyTestAction<Exception> onError) {

        new AsyncTask<Void, Void, GitHubRepo[]>() {

            private Exception exception ;

            @Override
            protected GitHubRepo[] doInBackground(Void ... params) {
                try {
                    Thread.sleep(2000);

                    //  TEST: Эмуляция ошибки при загрузке данных.
                    if (Math.random() > 0.5) {
                        throw new IOException();
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                    //  TEST: Эмуляция ошибки при загрузке данных.
                    exception = e;
                    return null;
                }
                return getRepos();
            }

            @Override
            protected void onPostExecute (GitHubRepo[] data) {
                if (exception != null) {
                    onError.call(exception);
                } else {
                    onNext.call(data);
                }
            }

        }.execute ();
    }

}

