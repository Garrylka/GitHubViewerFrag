package ru.roskvartal.garry.githubviewerfrag.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.roskvartal.garry.githubviewerfrag.R;
import ru.roskvartal.garry.githubviewerfrag.entity.GitHubRepo;


/**
 *  Фрагмент RepoLCEFragment (пришел на замену RepoListFragment), в котором спрятан Master GUI, взаимодействует с MainActivity.
 *  Вместо ListView содержит SwipeRefreshLayout, RecyclerView.
 *  + Заготовки view для LCE (loadingView, errorView - пока отключены).
 */
public class RepoLCEFragment extends Fragment  {

    private static final String LOGCAT_TAG    = "LIST";                         //  DEBUG
    private static final String ERR_MUST_IMPL = " должен реализовать RepoLCEFragment.OnFragmentInteractionListener";

    private Context listener;                                                  //  Слушатель - Активность.


    public RepoLCEFragment() {
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
        //if (savedState != null) {
        //    userName = savedState.getString(ARG_USER_NAME);
        //}

        //  Получение корневого объекта View Фрагмента. Теперь можно использовать метод findViewById().
        View rootView = inflater.inflate(R.layout.fragment_repo_lce, container, false);


        //  Получаем ссылки на GUI элементы.
        //  И назначаем обработчики.
        //final EditText editUserName = rootView.findViewById(R.id.editUserName);
        //ImageButton btnEnter = rootView.findViewById(R.id.btnEnter);


        //  Обработчик нажатия кнопки Enter.
        //View.OnClickListener clickListener = new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        //  TEST Проверка на пустую строку.
        //        //  Потом будет выбор: выводить все репо или по пользователю.
        //        userName = editUserName.getText().toString().trim();
        //        if (userName.isEmpty()) {
        //            Toast.makeText(listener, ERR_MUST_USER, Toast.LENGTH_SHORT).show();
        //            return;
        //        }
        //
        //        //  TODO: Запрос данных пользователя с сервера GitHub и вывод в список.
        //        Log.d(LOGCAT_TAG, "onClick(): " + userName);
        //    }
        //};
        //btnEnter.setOnClickListener(clickListener);


        //  Работа с SwipeRefresh.
        final SwipeRefreshLayout swipeRefresh = rootView.findViewById(R.id.swipeRefresh);

        SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //  TODO: Повторный запрос и вывод данных в список.
                swipeRefresh.setRefreshing(false);
            }
        };
        swipeRefresh.setOnRefreshListener(refreshListener);


        //  Работа со списком (RecyclerView).
        RecyclerView listRepo = rootView.findViewById(R.id.listRepo);

        //  Назначение Layout Manager.
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(listener);
        listRepo.setLayoutManager(layoutManager);

        //  Оптимизация.
        listRepo.setHasFixedSize(true);

        //  Назначение адаптера для вывода строк.
        RepoRecyclerViewAdapter listAdapter = new RepoRecyclerViewAdapter(GitHubRepo.repos);
        listRepo.setAdapter(listAdapter);

        //  Обработчик нажатия на элементе списка.
        //  Вызывает callback метод onRepoListItemClicked() в активности.
        RepoRecyclerViewAdapter.OnItemClickListener itemClickListener = new RepoRecyclerViewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {

                ((OnFragmentInteractionListener) listener).onRepoListItemClicked(position);
            }
        };
        listAdapter.setOnItemClickListener(itemClickListener);

        //  Добавление разделителя между элементами списка.
        //  У меня уже используется окантовка вокруг item в макете.
        //RecyclerView.ItemDecoration itemDivider = new DividerItemDecoration(
        //        listener, DividerItemDecoration.VERTICAL);
        //listRepo.addItemDecoration(itemDivider);

        return rootView;
    }


    //  Сохранение состояния Фрагмента перед уничтожением.
    //@Override
    //public void onSaveInstanceState(Bundle saveState) {
    //    saveState.putString(ARG_USER_NAME, userName);
    //    super.onSaveInstanceState(saveState);
    //}


    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


    //  Интерфейс с методом обратного вызова для развязки Фрагмента и Активности
    //  при обработке клика на элементе списка.
    //  Активность автоматически подписывается в событии onAttach() Фрагмента.
    //  Активность должна реализовать метод этого интерфейса!
    public interface OnFragmentInteractionListener {
        void onRepoListItemClicked(int repoID);
    }

}
