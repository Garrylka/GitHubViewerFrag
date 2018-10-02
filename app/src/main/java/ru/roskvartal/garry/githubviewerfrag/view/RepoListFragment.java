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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.roskvartal.garry.githubviewerfrag.R;
import ru.roskvartal.garry.githubviewerfrag.entity.GitHubRepo;


/**
 *  Фрагмент RepoListFragment, в котором спрятан Master GUI, взаимодействует с MainActivity.
 *  Переход на ButterKnife.
 */
public class RepoListFragment extends Fragment {

    private static final String LOGCAT_TAG    = "LIST";                         //  DEBUG
    private static final String ERR_MUST_IMPL = " должен реализовать RepoListFragment.OnRepoListFragmentListener";
    private static final String ERR_MUST_USER = "Укажите имя пользователя GitHub!";

    private static final String ARG_USER_NAME = "USER_NAME";

    private String   userNameSaved;
    private Context  listener;                                                  //  Слушатель - Активность.

    //  ButterKnife.
    @BindView(R.id.editUserName) EditText editUserName;
    @BindView(R.id.btnEnter) ImageButton btnEnter;
    @BindView(R.id.listRepo) ListView listRepo;

    //  ButterKnife!
    @OnClick(R.id.btnEnter)
    public void onClick() {
        //  TEST Проверка на пустую строку.
        //  Потом будет выбор: выводить все репо или по пользователю.
        userNameSaved = editUserName.getText().toString().trim();
        if (userNameSaved.isEmpty()) {
            Toast.makeText(listener, ERR_MUST_USER, Toast.LENGTH_SHORT).show();
            return;
        }

        //  TODO: Запрос данных пользователя с сервера GitHub и вывод в список.
        Log.d(LOGCAT_TAG, "onClick(): " + userNameSaved);
    }

    private Unbinder unbinder;



    public RepoListFragment() {
        //  Required empty public constructor
    }


    //  Интерфейс с методом обратного вызова для развязки Фрагмента и Активности
    //  при обработке клика на пункте ListView.
    //  Активность автоматически подписывается в событии onAttach() Фрагмента.
    public interface OnRepoListFragmentListener {
        void onRepoListItemClicked(int repoID);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnRepoListFragmentListener) {
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
            userNameSaved = savedState.getString(ARG_USER_NAME);
        }

        //  Получение корневого объекта View Фрагмента. Теперь можно использовать метод findViewById().
        View rootView = inflater.inflate(R.layout.fragment_repo_list, container, false);

        //  ButterKnife
        unbinder = ButterKnife.bind(this, rootView);

        //  ButterKnife - Получаем ссылки на GUI элементы.
        //final EditText editUserName = rootView.findViewById(R.id.editUserName);
        //ImageButton btnEnter = rootView.findViewById(R.id.btnEnter);

        //  И назначаем обработчики.

        //  ButterKnife - Обработчик нажатия кнопки Enter.
        /*
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  TEST Проверка на пустую строку.
                //  Потом будет выбор: выводить все репо или по пользователю.
                userNameSaved = editUserName.getText().toString().trim();
                if (userNameSaved.isEmpty()) {
                    Toast.makeText(listener, ERR_MUST_USER, Toast.LENGTH_SHORT).show();
                    return;
                }

                //  TODO: Запрос данных пользователя с сервера GitHub и вывод в список.
                Log.d(LOGCAT_TAG, "onClick(): " + userNameSaved);
            }
        };
        btnEnter.setOnClickListener(clickListener);
        */


        //  Работа со списком:
        //  Назначение адаптера для вывода строк.
        //ButterKnife - ListView listRepo = rootView.findViewById(R.id.listRepo);

        //  TEST Пока просто выводим String из массива при onCreateView.
        //  Потом этот код будет в другом событии (и возможно с разными row_list_repos.xml).
        //  ArrayAdapter<GitHubRepo> listAdapter = new ArrayAdapter<>(
        //    listener,
        //    android.R.layout.simple_list_item_1,
        //    GitHubRepo.repos);


        //  NEW ListView с иконками (указал свой row_list_repo макет).
        ArrayAdapter<GitHubRepo> listAdapter = new ArrayAdapter<GitHubRepo>(
                listener, R.layout.row_list_repo, R.id.textRepoName, GitHubRepo.repos) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View rootView = super.getView(position, convertView, parent);

                //  ACHTUNG! ButterKnife Не прокатил в анонимном классе!
                //  Надо создать свой адаптер от BaseAdapter и использовать паттерн ViewHolder:
                //  Примеры:
                //      http://jakewharton.github.io/butterknife/
                //      https://guides.codepath.com/android/Using-a-BaseAdapter-with-ListView#listview-row-layout

                //  User Avatar
                ImageView userAvatar = rootView.findViewById(R.id.imgUserAvatar);
                userAvatar.setImageResource(GitHubRepo.repos[position].getOwnerAvatarId());

                //  User Name
                TextView userName = rootView.findViewById(R.id.textUserName);
                userName.setText(GitHubRepo.repos[position].getOwnerName());

                //  Repo Name
                TextView repoName = rootView.findViewById(R.id.textRepoName);
                repoName.setText(GitHubRepo.repos[position].getRepoName());

                //  Repo Desc
                TextView repoDesc = rootView.findViewById(R.id.textRepoDesc);
                repoDesc.setText(GitHubRepo.repos[position].getRepoDesc());

                //  Repo Url
                TextView repoUrl = rootView.findViewById(R.id.textRepoUrl);
                repoUrl.setText(GitHubRepo.repos[position].getRepoUrl());

                return rootView;
            }
        };
        listRepo.setAdapter(listAdapter);


        //  Обработчик нажатия на элемент списка.
        //  Вызывает callback метод onRepoListItemClicked() в активности.
        AdapterView.OnItemClickListener itemClickListener =
                (parent, view, position, id) -> ((OnRepoListFragmentListener) listener).onRepoListItemClicked((int) id);
        listRepo.setOnItemClickListener(itemClickListener);

        return rootView;
    }


    //  Сохранение состояния Фрагмента перед уничтожением.
    @Override
    public void onSaveInstanceState(@NonNull Bundle saveState) {
        saveState.putString(ARG_USER_NAME, userNameSaved);
        super.onSaveInstanceState(saveState);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


    //  ButterKnife. Set the views to null.
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
