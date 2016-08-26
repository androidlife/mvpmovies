package com.wordpress.laaptu.mvp.movie.searchmovies;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.wordpress.laaptu.mvp.movie.R;
import com.wordpress.laaptu.mvp.movie.base.BaseFragment;
import com.wordpress.laaptu.mvp.movie.base.model.SearchedMovie;
import com.wordpress.laaptu.mvp.movie.widgets.adapters.EndlessRecyclerViewScrollListener;
import com.wordpress.laaptu.mvp.movie.widgets.adapters.SearchedMovieAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import timber.log.Timber;

/**
 */
public class SearchMoviesFragment extends BaseFragment implements SearchMoviesMVP.View {

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.info_tv)
    TextView infoTextView;
    @BindView(R.id.movie_list)
    RecyclerView movieListView;

    private static final String PAGE_NUMBER = "pageNumber", MOVIE_LIST = "moviesList", QUERY_TEXT = "queryText";
    private String queryText = "";

    @Override
    public int getLayoutId() {
        return R.layout.movie_list;
    }

    public SearchMoviesFragment() {

    }

    public static SearchMoviesFragment getInstance(Bundle params) {
        SearchMoviesFragment movieListFragment = new SearchMoviesFragment();
        movieListFragment.setArguments(params);
        return movieListFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(savedInstanceState);
    }


    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    private SearchedMovieAdapter movieAdapter;
    private static final int threshold = 3, pageLimit = 10;
    private int pageNumber = 0;
    private boolean isLoading = false;
    private SearchMoviesPresenter presenter;

    private void initView(Bundle params) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        movieListView.setLayoutManager(linearLayoutManager);
        swipeRefreshLayout.setColorSchemeColors(getResources().getIntArray(R.array.swipe_progress_colors));
        swipeRefreshLayout.setEnabled(false);

        ArrayList<SearchedMovie> movies = new ArrayList<>();
        if (params != null && params.containsKey(PAGE_NUMBER)) {
            pageNumber = params.getInt(PAGE_NUMBER);
            movies = params.getParcelableArrayList(MOVIE_LIST);
            queryText = params.getString(QUERY_TEXT);
        }

        movieAdapter = new SearchedMovieAdapter(getContext(), movies);
        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager, movieAdapter, threshold) {
            @Override
            public void onLoadMore(int totalItemsCount) {
                if (!isLoading) {
                    Timber.d("onLoad More , page =%d and isLoadShow =%b", pageNumber, movieAdapter.isLoadMore());
                    //searchByMovies(queryText, pageNumber + 1);
                    loadMore(pageNumber + 1);
                }
            }
        };
        movieListView.addOnScrollListener(endlessRecyclerViewScrollListener);
        movieListView.setAdapter(movieAdapter);

        presenter = new SearchMoviesPresenter(this);

    }

    private void setRefreshing(final boolean refresh) {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(refresh);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (movieAdapter != null && movieAdapter.getItemCount() > 0) {
            outState.putInt(PAGE_NUMBER, pageNumber);
            outState.putParcelableArrayList(MOVIE_LIST, (ArrayList<? extends Parcelable>) movieAdapter.getItems());
            outState.putString(QUERY_TEXT, queryText);
        }
    }

    private void showInfoText(boolean show, String info) {
        int visibility = show ? View.VISIBLE : View.GONE;
        if (infoTextView.getVisibility() == visibility)
            return;
        infoTextView.setVisibility(visibility);
        if (show && info != null)
            infoTextView.setText(info);
    }


    //new search
    public void searchByMovies(String searchText) {
        if (queryText.equals(searchText) || TextUtils.isEmpty(searchText))
            return;
        presenter.beginNewSearch(searchText, pageLimit);
    }


    //pagination
    private void loadMore(final int page) {
        presenter.loadMore(queryText, page, pageLimit);
    }


    //MVP's view implementation
    public void setAllEmptyNProgress() {
        movieAdapter.setLoadMore(false);
        movieAdapter.set(new ArrayList<SearchedMovie>());
        setRefreshing(true);
        showInfoText(false, null);
        pageNumber = 0;

    }

    @Override
    public void setAdapter(ArrayList<SearchedMovie> movies, String searchText, int page) {
        movieAdapter.add(movies);
        pageNumber = page;
        queryText = searchText;
    }


    public void hideProgressErrorIfAny() {
        isLoading = false;
        setRefreshing(false);
        showInfoText(false, null);
    }

    @Override
    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public void setError() {
        if (movieAdapter.getItemCount() == 0)
            showInfoText(true, getString(R.string.error_searching_movies));
        else if (movieAdapter.isLoadMore())
            movieAdapter.setLoadMore(false);
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
