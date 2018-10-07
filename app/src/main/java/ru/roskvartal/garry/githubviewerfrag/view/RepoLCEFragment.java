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

import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.MvpLceViewStateFragment;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.data.RetainingLceViewState;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import ru.roskvartal.garry.githubviewerfrag.R;
import ru.roskvartal.garry.githubviewerfrag.model.RepoModelImpl;
import ru.roskvartal.garry.githubviewerfrag.model.api.GitHubService;
import ru.roskvartal.garry.githubviewerfrag.model.entity.GitHubRepoMaster;
import ru.roskvartal.garry.githubviewerfrag.presenter.ReposPresenter;
import ru.roskvartal.garry.githubviewerfrag.presenter.ReposPresenterImpl;


/**
 *  Переход на RxJava 2 и List<GitHubRepo>.
 *  Переход на Mosby MVP LCE ViewState.
 *  Переход на ButterKnife.
 *  Фрагмент RepoLCEFragment (пришел на замену RepoListFragment), в котором спрятан Master GUI,
 *  взаимодействует с MainActivity.
 *  Вместо ListView содержит SwipeRefreshLayout, RecyclerView.
 */
public class RepoLCEFragment
        extends MvpLceViewStateFragment<RecyclerView, List<GitHubRepoMaster>, ReposView, ReposPresenter>
        implements ReposView {

    //private static final String LOGCAT_TAG    = "LIST";                         //  DEBUG
    private static final String ERR_MUST_IMPL = " должен реализовать RepoLCEFragment.OnRepoListFragmentListener";
    private static final String ERR_UNKNOWN_ERR = "Неизвестная ошибка!";

    private Context listener;                                                   //  Слушатель - Активность.

    private RepoRecyclerViewAdapter listAdapter;                                //  Адаптер RecyclerView.

    //  ButterKnife.
    @BindView(R.id.swipeRefresh) SwipeRefreshLayout swipeRefresh;

    private Unbinder unbinder;



    //  Интерфейс с методом обратного вызова для развязки Фрагмента и Активности
    //  при обработке клика на элементе списка.
    //  Активность автоматически подписывается в событии onAttach() Фрагмента.
    //  Активность должна реализовать метод этого интерфейса!
    public interface OnRepoListFragmentListener {
        void onRepoListItemClicked(int repoID);
    }


    public RepoLCEFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public ReposPresenter createPresenter() {
        return new ReposPresenterImpl(
                new RepoModelImpl(
                        new Retrofit.Builder().baseUrl(GitHubService.API_ENDPOINT)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(GitHubService.class)
                ));
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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  Переход на Mosby MVP LCE ViewState.
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedState) {

        return inflater.inflate(R.layout.fragment_repo_lce, container, false);
    }


    //  Инициализации Фрагмента.
    //  При использовании Mosby LCE всю инициализацию надо проводить в методе onViewCreated()!
    //  Иначе в onCreateView() ошибка на contentView.setOnRefreshListener(refreshListener) - contentView еще не создан!
    @Override
    public void onViewCreated(View rootView, @Nullable Bundle savedInstance) {

        super.onViewCreated(rootView, savedInstance);

        //  ButterKnife.
        unbinder = ButterKnife.bind(this, rootView);


        //  Работа с SwipeRefresh.
        //ButterKnife - swipeRefresh = rootView.findViewById(R.id.swipeRefresh);

        //  Обработчик свайпа: Повторный запрос и вывод данных в список.
        SwipeRefreshLayout.OnRefreshListener refreshListener = () -> loadData(true);
        swipeRefresh.setOnRefreshListener(refreshListener);


        //  Работа со списком RecyclerView ( Теперь это - contentView ! ).
        //contentView - RecyclerView listRepo = rootView.findViewById(R.id.listRepo);

        //  Назначение Layout Manager.
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(listener);
        contentView.setLayoutManager(layoutManager);

        //  Оптимизация.
        contentView.setHasFixedSize(true);

        //  Назначение адаптера для вывода строк.
        listAdapter = new RepoRecyclerViewAdapter();

        //  Обработчик нажатия на элементе списка.
        //  Вызывает callback метод onRepoListItemClicked() в активности.
        RepoRecyclerViewAdapter.OnItemClickListener itemClickListener =
                (view, position) -> ((OnRepoListFragmentListener) listener).onRepoListItemClicked(position);
        listAdapter.setOnItemClickListener(itemClickListener);
        contentView.setAdapter(listAdapter);

        //  Добавление разделителя между элементами списка.
        //  У меня уже используется окантовка вокруг item в макете.
        //RecyclerView.ItemDecoration itemDivider = new DividerItemDecoration(
        //        listener, DividerItemDecoration.VERTICAL);
        //listRepo.addItemDecoration(itemDivider);


        //  LCE!
        //  Переход на Mosby MVP LCE ViewState.
        //loadData(false);
    }


    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }


    //  ButterKnife. Set the views to null.
    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }


    //  Переход на MVP LCE.
    //  MvpLceView already defines LCE methods:
    //      void showLoading(boolean pullToRefresh)
    //      void showError(Throwable t, boolean pullToRefresh)
    //      void setData(List<Country> data)
    //      void showContent()

    //  Развязка Model и View через Presenter!
    //  Загрузка данных.
    @Override
    public void loadData(boolean pullToRefresh) {

        presenter.loadRepos(pullToRefresh);
    }


    @Override
    public void setData(List<GitHubRepoMaster> repos) {
        //  FIXME Rx Добавление данных порциями buffer(BUF_SIZE)!
        listAdapter.addRepos(repos);
        listAdapter.notifyDataSetChanged();
    }


    @Override
    public void showLoading(boolean pullToRefresh) {
        super.showLoading(pullToRefresh);
        swipeRefresh.setRefreshing(pullToRefresh);
    }


    @Override
    public void showContent() {
        swipeRefresh.setRefreshing(false);
        super.showContent();
    }


    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        swipeRefresh.setRefreshing(false);
        super.showError(e, pullToRefresh);
    }


    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        String message = e.toString();
        return (message.isEmpty() ? ERR_UNKNOWN_ERR : message);
    }


    //  Переход на Mosby MVP LCE ViewState.
    @NonNull
    @Override
    public LceViewState<List<GitHubRepoMaster>, ReposView> createViewState() {
        return new RetainingLceViewState<>();
    }


    @Nullable
    @Override
    public List<GitHubRepoMaster> getData() {
        return (listAdapter == null ? null : listAdapter.getRepos());
    }


    //  Очистка данных в адаптере при SwipeRefresh.
    @Override
    public void clearViewData() {
        if (getData() != null) {
            getData().clear();
            listAdapter.notifyDataSetChanged();
        }
    }


    //  TEST
    //  Возвращает размер нашего набора данных.
    @Override
    public int getDataCount() {
        return (listAdapter == null ? 0 : listAdapter.getItemCount());
    }
}
