package ru.roskvartal.garry.githubviewerfrag.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import ru.roskvartal.garry.githubviewerfrag.R;
import ru.roskvartal.garry.githubviewerfrag.entity.GitHubRepo;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RepoListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RepoListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RepoListFragment extends Fragment {

    private static final String LOGCAT_TAG    = "LIST";                         //  DEBUG
    private static final String ERR_MUST_IMPL = " должен реализовать RepoListFragment.OnFragmentInteractionListener";
    private static final String ERR_MUST_USER = "Укажите имя пользователя GitHub!";

    private static final String ARG_USER_NAME = "USER_NAME";
    private static final String ARG_REPO_ID   = "REPO_ID";

    private String   userName;
    private int      repoID;
    private Context  listener;                                                  //  Слушатель - Активность.

    public RepoListFragment() {
        // Required empty public constructor
    }

    /**
     * Пока пусть сохраняются userName и repoID!
     * @param userName Имя пользователя GitHub.
     * @param repoID ID репозитория.
     * @return Новый экземпляр RepoListFragment.
     */
     public static RepoListFragment newInstance(String userName, int repoID) {
        RepoListFragment fragment = new RepoListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_NAME, userName);
        args.putInt(ARG_REPO_ID, repoID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = context;
        } else {
            throw new RuntimeException(context.toString() + ERR_MUST_IMPL);
        }
    }

    //  Пока пусть сохраняются userName и repoID!
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userName = getArguments().getString(ARG_USER_NAME);
            repoID   = getArguments().getInt(ARG_REPO_ID);
        }
    }

    //  Используется для инициализации Фрагмента.
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment.
        //return inflater.inflate(R.layout.fragment_repo_list, container, false);

        //  Получение корневого объекта View Фрагмента. Теперь можно использовать метод findViewById().
        View rootView = inflater.inflate(R.layout.fragment_repo_list, container, false);

        //  Получаем ссылки на GUI элементы.
        //  И назначаем обработчики.
        final EditText editUserName = (EditText) rootView.findViewById(R.id.editUserName);
        ImageButton btnEnter = (ImageButton) rootView.findViewById(R.id.btnEnter);

        //  Обработчик нажатия кнопки Enter.
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  TEST Проверка на пустую строку.
                //  Потом будет выбор: выводить все репо или по пользователю.
                userName = editUserName.getText().toString().trim();
                if (userName.isEmpty()) {
                    Toast.makeText(listener, ERR_MUST_USER, Toast.LENGTH_SHORT).show();
                    return;
                }

                //  TODO: Запрос данных пользователя с сервера GitHub и вывод в список.
                Log.d(LOGCAT_TAG, "onClick(): " + userName);
            }
        };
        btnEnter.setOnClickListener(clickListener);

        //  Работа со списком:
        //  Назначение адаптера для вывода строк.
        ListView listRepo = (ListView) rootView.findViewById(R.id.listRepo);

        //  TEST Пока просто выводим String из массива при onCreateView.
        //  Потом этот код будет в другом событии (и возможно с разными row_list_repos.xml).
        ArrayAdapter<GitHubRepo> listAdapter = new ArrayAdapter<>(
                listener,
                android.R.layout.simple_list_item_1,
                GitHubRepo.repos);
        listRepo.setAdapter(listAdapter);

        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onRepoListItemClicked(int repoID);
    }
}
