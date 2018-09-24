package ru.roskvartal.garry.githubviewerfrag.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import ru.roskvartal.garry.githubviewerfrag.R;


/**
 *  DetailActivity взаимодействует с фрагментом RepoDetailFragment, в котором спрятан Detail GUI.
 */
public class DetailActivity extends AppCompatActivity {

    private static final String LOGCAT_TAG = "DETAIL";                          //  DEBUG

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //  Получение repoID.
        Bundle bundle = getIntent().getExtras();
        if (bundle == null || !bundle.containsKey(RepoDetailFragment.ARG_REPO_ID)) {
            finish();                                                           //  Ничего не передано, выходим из Activity!
            return;
        }

        int repoID = bundle.getInt(RepoDetailFragment.ARG_REPO_ID);

        Log.d(LOGCAT_TAG, "Detail Activity: repoId = " + repoID);

        //  Поиск дочернего RepoDetailFragment для передачи ему repoId.
        //  Еще есть метод getFragmentManager(), но для либы support надо использовать getSupportFragmentManager().
        RepoDetailFragment detailFragment =
                (RepoDetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentRepoDetail);

        //  Передаем repoId Фрагменту, он заполняет представления правильным контентом.
        detailFragment.setRepoId(repoID);
    }
}
