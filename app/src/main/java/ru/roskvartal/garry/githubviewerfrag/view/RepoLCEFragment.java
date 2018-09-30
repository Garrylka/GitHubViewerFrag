package ru.roskvartal.garry.githubviewerfrag.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby3.mvp.lce.MvpLceFragment;

import ru.roskvartal.garry.githubviewerfrag.R;
import ru.roskvartal.garry.githubviewerfrag.entity.GitHubRepo;
import ru.roskvartal.garry.githubviewerfrag.model.RepoModelImpl;
import ru.roskvartal.garry.githubviewerfrag.presenter.ReposPresenter;
import ru.roskvartal.garry.githubviewerfrag.presenter.ReposPresenterImpl;


/**
 *  Переход на Mosby MVP LCE.
 *  Фрагмент RepoLCEFragment (пришел на замену RepoListFragment), в котором спрятан Master GUI,
 *  взаимодействует с MainActivity.
 *  Вместо ListView содержит SwipeRefreshLayout, RecyclerView.
 *  + Заготовки view для LCE (loadingView, errorView - пока отключены).
 */
public class RepoLCEFragment
        extends MvpLceFragment<SwipeRefreshLayout, GitHubRepo[], ReposView, ReposPresenter>
        implements ReposView {

    //private static final String LOGCAT_TAG    = "LIST";                         //  DEBUG
    private static final String ERR_MUST_IMPL = " должен реализовать RepoLCEFragment.OnFragmentInteractionListener";

    private Context listener;                                                   //  Слушатель - Активность.

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


    @NonNull
    @Override
    public ReposPresenter createPresenter() {
        return new ReposPresenterImpl(new RepoModelImpl());
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedState) {

        return inflater.inflate(R.layout.fragment_repo_lce, container, false);
    }


    //  Инициализации Фрагмента.
    @Override
    public void onViewCreated(View rootView, @Nullable Bundle savedInstance) {

        super.onViewCreated(rootView, savedInstance);

        //  Работа с SwipeRefresh ( Теперь это - contentView ! ).
        //swipeRefresh = rootView.findViewById(R.id.swipeRefresh);

        //  Обработчик свайпа: Повторный запрос и вывод данных в список.
        SwipeRefreshLayout.OnRefreshListener refreshListener = () -> loadData(true);
        contentView.setOnRefreshListener(refreshListener);


        //  Работа со списком (RecyclerView).
        RecyclerView listRepo = rootView.findViewById(R.id.listRepo);

        //  Назначение Layout Manager.
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(listener);
        listRepo.setLayoutManager(layoutManager);

        //  Оптимизация.
        listRepo.setHasFixedSize(true);

        //  Назначение адаптера для вывода строк.
        listAdapter = new RepoRecyclerViewAdapter();
        listRepo.setAdapter(listAdapter);

        //  Обработчик нажатия на элементе списка.
        //  Вызывает callback метод onRepoListItemClicked() в активности.
        RepoRecyclerViewAdapter.OnItemClickListener itemClickListener =
                (view, position) -> ((OnFragmentInteractionListener) listener).onRepoListItemClicked(position);
        listAdapter.setOnItemClickListener(itemClickListener);

        //  Добавление разделителя между элементами списка.
        //  У меня уже используется окантовка вокруг item в макете.
        //RecyclerView.ItemDecoration itemDivider = new DividerItemDecoration(
        //        listener, DividerItemDecoration.VERTICAL);
        //listRepo.addItemDecoration(itemDivider);


        //  LCE!
        loadData(false);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


    //  Переход на MVP LCE.
    //  MvpLceView already defines LCE methods:
    //      void showLoading(boolean pullToRefresh)
    //      void showError(Throwable t, boolean pullToRefresh)
    //      void setData(List<Country> data)
    //      void showContent()

    //  Развязка Model и View через Presenter!
    //  В этом простом случае из модели в представление просто передается ссылка на тестовый массив repos.
    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadRepos(pullToRefresh);
    }


    @Override
    public void setData(GitHubRepo[] repos) {
        listAdapter.setRepos(repos);
        listAdapter.notifyDataSetChanged();
    }


    @Override
    public void showLoading(boolean pullToRefresh) {
        super.showLoading(pullToRefresh);
        contentView.setRefreshing(pullToRefresh);
    }

    @Override
    public void showContent() {
        super.showContent();
        contentView.setRefreshing(false);
    }


    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        super.showError(e, pullToRefresh);
        contentView.setRefreshing(false);
    }


    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        String message = e.getMessage();
        return (message == null ? "Unknown error!" : message);
    }

}
