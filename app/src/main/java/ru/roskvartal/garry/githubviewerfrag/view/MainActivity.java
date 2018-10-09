package ru.roskvartal.garry.githubviewerfrag.view;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import ru.roskvartal.garry.githubviewerfrag.R;


/**
 *  MainActivity взаимодействует с фрагментом RepoListFragment, в котором спрятан Master GUI.
 */
public class MainActivity extends AppCompatActivity
        implements RepoLCEFragment.OnRepoListFragmentListener {

    private static final String LOGCAT_TAG = "MAIN";                            //  DEBUG

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(LOGCAT_TAG, "onCreate()");                                        //  DEBUG
    }

    //  Обработчик клика на списке репозиториев в RepoListFragment.
    @Override
    public void onRepoListItemClicked(int repoID) {

        //  Запуск активности DetailActivity с дополнительной информацией о выбранном репозитории.
        //  Передаем в активность ID репозитория.
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(RepoDetailFragment.ARG_REPO_ID, repoID);
        startActivity(intent);
    }
}
