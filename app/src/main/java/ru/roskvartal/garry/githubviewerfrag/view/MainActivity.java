package ru.roskvartal.garry.githubviewerfrag.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import ru.roskvartal.garry.githubviewerfrag.R;

/*
    Главная активность, на которой пользователь должен ввести имя пользователя GitHub.
 */
public class MainActivity extends AppCompatActivity
        implements RepoListFragment.OnFragmentInteractionListener {

    private static final String LOGCAT_TAG = "MAIN";                            //  DEBUG

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(LOGCAT_TAG, "onCreate()");                                        //  DEBUG
    }

    @Override
    public void onRepoListItemClicked(int repoID) {
        //  TODO: Вызов детального фрагмента.
    }
}
