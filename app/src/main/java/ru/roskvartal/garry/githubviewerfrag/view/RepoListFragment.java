package ru.roskvartal.garry.githubviewerfrag.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import ru.roskvartal.garry.githubviewerfrag.R;
import ru.roskvartal.garry.githubviewerfrag.entity.GitHubRepo;


/**
 *  Фрагмент RepoListFragment, в котором спрятан Master GUI, взаимодействует с MainActivity.
 */
public class RepoListFragment extends Fragment {

    private static final String LOGCAT_TAG    = "LIST";                         //  DEBUG
    private static final String ERR_MUST_IMPL = " должен реализовать RepoListFragment.OnFragmentInteractionListener";
    private static final String ERR_MUST_USER = "Укажите имя пользователя GitHub!";

    private static final String ARG_USER_NAME = "USER_NAME";

    private String   userName;
    private Context  listener;                                                  //  Слушатель - Активность.


    public RepoListFragment() {
        // Required empty public constructor
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


    //  Используется для инициализации Фрагмента.
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedState) {

        //  Восстановление данных при повороте экрана.
        if (savedState != null) {
            userName = savedState.getString(ARG_USER_NAME);
        }

        //  OLD Inflate the layout for this fragment.
        //return inflater.inflate(R.layout.fragment_repo_list, container, false);

        //  Получение корневого объекта View Фрагмента. Теперь можно использовать метод findViewById().
        View rootView = inflater.inflate(R.layout.fragment_repo_list, container, false);


        //  Получаем ссылки на GUI элементы.
        //  И назначаем обработчики.
        final EditText editUserName = rootView.findViewById(R.id.editUserName);
        ImageButton btnEnter = rootView.findViewById(R.id.btnEnter);


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
        ListView listRepo = rootView.findViewById(R.id.listRepo);

        //  TEST Пока просто выводим String из массива при onCreateView.
        //  Потом этот код будет в другом событии (и возможно с разными row_list_repos.xml).
        //  ArrayAdapter<GitHubRepo> listAdapter = new ArrayAdapter<>(
        //    listener,
        //    android.R.layout.simple_list_item_1,
        //    GitHubRepo.repos);


        //  NEW ListView с иконками (указал свой row_list_repo макет).
        ArrayAdapter<GitHubRepo> listAdapter = new ArrayAdapter<GitHubRepo>(
                listener,
                R.layout.row_list_repo,
                R.id.textRepoName,
                GitHubRepo.repos) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = super.getView(position, convertView, parent);

                //  User Avatar
                ImageView userAvatar = view.findViewById(R.id.imgUserAvatar);
                userAvatar.setImageResource(GitHubRepo.repos[position].getOwnerAvatarId());

                //  User Name
                TextView userName = view.findViewById(R.id.textUserName);
                userName.setText(GitHubRepo.repos[position].getOwnerName());

                //  Repo Name
                TextView repoName = view.findViewById(R.id.textRepoName);
                repoName.setText(GitHubRepo.repos[position].getRepoName());

                //  Repo Desc
                TextView repoDesc = view.findViewById(R.id.textRepoDesc);
                repoDesc.setText(GitHubRepo.repos[position].getRepoDesc());

                //  Repo Url
                TextView repoUrl = view.findViewById(R.id.textRepoUrl);
                repoUrl.setText(GitHubRepo.repos[position].getRepoUrl());

                return view;
            }
        };
        listRepo.setAdapter(listAdapter);


        //  Обработчик нажатия на элемент списка.
        //  Вызывает callback метод onRepoListItemClicked() в активности.
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ((OnFragmentInteractionListener) listener).onRepoListItemClicked((int) id);
            }
        };
        listRepo.setOnItemClickListener(itemClickListener);

        return rootView;
    }


    //  Сохранение состояния Фрагмента перед уничтожением.
    @Override
    public void onSaveInstanceState(Bundle saveState) {
        saveState.putString(ARG_USER_NAME, userName);
        super.onSaveInstanceState(saveState);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


    //  Интерфейс с методом обратного вызова для развязки Фрагмента и Активности
    //  при обработке клика на пункте ListView.
    //  Активность автоматически подписывается в событии onAttach() Фрагмента.
    public interface OnFragmentInteractionListener {
        void onRepoListItemClicked(int repoID);
    }
}
