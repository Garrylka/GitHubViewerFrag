package ru.roskvartal.garry.githubviewerfrag.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import ru.roskvartal.garry.githubviewerfrag.model.RepoModel;
import ru.roskvartal.garry.githubviewerfrag.model.RepoModelImpl;
import ru.roskvartal.garry.githubviewerfrag.presenter.ReposPresenter;
import ru.roskvartal.garry.githubviewerfrag.presenter.ReposPresenterImpl;


/**
 *  Фрагмент RepoLCEFragment (пришел на замену RepoListFragment), в котором спрятан Master GUI, взаимодействует с MainActivity.
 *  Вместо ListView содержит SwipeRefreshLayout, RecyclerView.
 *  + Заготовки view для LCE (loadingView, errorView - пока отключены).
 */
public class RepoLCEFragment extends Fragment implements ReposView {

    private static final String LOGCAT_TAG    = "LIST";                         //  DEBUG
    private static final String ERR_MUST_IMPL = " должен реализовать RepoLCEFragment.OnFragmentInteractionListener";

    private Context listener;                                                   //  Слушатель - Активность.

    private ReposPresenter presenter;

    private SwipeRefreshLayout swipeRefresh;

    private RepoRecyclerViewAdapter listAdapter;                                //  Адаптер RecyclerView.


    //  Интерфейс с методом обратного вызова для развязки Фрагмента и Активности
    //  при обработке клика на элементе списка.
    //  Активность автоматически подписывается в событии onAttach() Фрагмента.
    //  Активность должна реализовать метод этого интерфейса!
    public interface OnFragmentInteractionListener {
        void onRepoListItemClicked(int repoID);
    }


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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listAdapter = new RepoRecyclerViewAdapter();

        RepoModel model = new RepoModelImpl();
        presenter = new ReposPresenterImpl(model);
    }


    //  Используется для инициализации Фрагмента.
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedState) {

        //  Получение корневого объекта View Фрагмента. Теперь можно использовать метод findViewById().
        View rootView = inflater.inflate(R.layout.fragment_repo_lce, container, false);


        //  Работа с SwipeRefresh.
        swipeRefresh = rootView.findViewById(R.id.swipeRefresh);

        SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //  TODO: Повторный запрос и вывод данных в список.
                loadData();
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
        listRepo.setAdapter(listAdapter);

        //  Обработчик нажатия на элементе списка.
        //  Вызывает callback метод onRepoListItemClicked() в активности.
        RepoRecyclerViewAdapter.OnItemClickListener itemClickListener =
                new RepoRecyclerViewAdapter.OnItemClickListener() {

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


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //  Передача ссылки на массив repos в адаптер.
        loadData();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


    //  Развязка Model и View через Presenter!
    //  В этом простом случае из модели в представление просто передается ссылка на тестовый массив repos.
    //  Параметр this т.к. фрагмент implements ReposView и метод setRepos() описан ниже.
    public void loadData() {
        presenter.loadRepos(this);
    }


    //  Переход на MVP.
    @Override
    public void setRepos(GitHubRepo[] repos) {
        listAdapter.setRepos(repos);
        listAdapter.notifyDataSetChanged();
    }


    @Override
    public void showContent() {
        swipeRefresh.setRefreshing(false);
    }


}
