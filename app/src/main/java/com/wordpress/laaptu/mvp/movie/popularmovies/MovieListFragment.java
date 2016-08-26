package com.wordpress.laaptu.mvp.movie.popularmovies;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wordpress.laaptu.mvp.movie.R;
import com.wordpress.laaptu.mvp.movie.base.BaseFragment;
import com.wordpress.laaptu.mvp.movie.base.model.Movie;
import com.wordpress.laaptu.mvp.movie.widgets.adapters.EndlessRecyclerViewScrollListener;
import com.wordpress.laaptu.mvp.movie.widgets.adapters.MovieAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

/**
 */
public class MovieListFragment extends BaseFragment implements PopularMoviesMVP.View {

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.info_tv)
    TextView infoTextView;
    @BindView(R.id.movie_list)
    RecyclerView movieListView;

    private static final String PAGE_NUMBER = "pageNumber", MOVIE_LIST = "moviesList";


    private PopularMoviesPresenter presenter;

    @Override
    public int getLayoutId() {
        return R.layout.movie_list;
    }

    public MovieListFragment() {

    }

    public static MovieListFragment getInstance(Bundle params) {
        MovieListFragment movieListFragment = new MovieListFragment();
        movieListFragment.setArguments(params);
        return movieListFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(savedInstanceState);
    }


    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    private MovieAdapter movieAdapter;
    private static final int threshold = 3, pageLimit = 10;
    private int pageNumber = 0;
    private boolean isLoading = false;

    private void initView(Bundle params) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        movieListView.setLayoutManager(linearLayoutManager);
        swipeRefreshLayout.setColorSchemeColors(getResources().getIntArray(R.array.swipe_progress_colors));
        swipeRefreshLayout.setEnabled(false);

        ArrayList<Movie> movies = new ArrayList<>();
        if (params != null && params.containsKey(PAGE_NUMBER)) {
            pageNumber = params.getInt(PAGE_NUMBER);
            movies = params.getParcelableArrayList(MOVIE_LIST);
        }

        movieAdapter = new MovieAdapter(getContext(), movies);
        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager, movieAdapter, threshold) {
            @Override
            public void onLoadMore(int totalItemsCount) {
                if (!isLoading) {
                    Timber.d("onLoad More , page =%d and isLoadShow =%b", pageNumber, movieAdapter.isLoadMore());
                    //fetchPopularMovies(pageNumber + 1);
                    presenter.fetchPopularMovies(pageNumber + 1, pageLimit);
                }
            }
        };
        movieListView.addOnScrollListener(endlessRecyclerViewScrollListener);
        movieListView.setAdapter(movieAdapter);

        presenter = new PopularMoviesPresenter(this);
        if (movies.size() == 0) {
            presenter.initMovieLoad(pageLimit);
        }
    }

    @OnClick({R.id.info_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.info_tv:
                presenter.initMovieLoad(pageLimit);
                break;
        }
    }

    private void setRefreshing(final boolean refresh) {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(refresh);
            }
        });
    }


    private void showInfoText(boolean show, String info) {
        int visibility = show ? View.VISIBLE : View.GONE;
        if (infoTextView.getVisibility() == visibility)
            return;
        infoTextView.setVisibility(visibility);
        if (show && info != null)
            infoTextView.setText(info);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (movieAdapter != null && movieAdapter.getItemCount() > 0) {
            outState.putInt(PAGE_NUMBER, pageNumber);
            outState.putParcelableArrayList(MOVIE_LIST, (ArrayList<? extends Parcelable>) movieAdapter.getItems());
        }
    }

    //View implementation
    public void showFirstLoad() {
        setRefreshing(true);
        pageNumber = 0;
        showInfoText(false, null);
    }

    public void setAdapter(ArrayList<Movie> movies, int page) {
        movieAdapter.add(movies);
        pageNumber = page;
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
        if (movieAdapter.getItemCount() == 0) {
            showInfoText(true, getString(R.string.error_loading_movies));
        } else if (movieAdapter.isLoadMore()) {
            movieAdapter.setLoadMore(false);
        }
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
