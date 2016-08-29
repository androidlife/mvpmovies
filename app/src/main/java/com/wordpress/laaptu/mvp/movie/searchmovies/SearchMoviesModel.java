package com.wordpress.laaptu.mvp.movie.searchmovies;

import com.wordpress.laaptu.mvp.movie.base.model.SearchedMovie;
import com.wordpress.laaptu.mvp.movie.base.network.ApiEndpoints;
import com.wordpress.laaptu.mvp.movie.base.network.RequestCallback;
import com.wordpress.laaptu.mvp.movie.base.network.retrofit.RetrofitCallback;
import com.wordpress.laaptu.mvp.movie.base.network.retrofit.RetrofitManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

/**
 */
public class SearchMoviesModel implements SearchMoviesMVP.Model {

    private SearchMoviesMVP.Presenter.Model presenter;
    private Call<ArrayList<SearchedMovie>> networkCall;

    public SearchMoviesModel(SearchMoviesMVP.Presenter.Model presenter) {
        this.presenter = presenter;
    }

    @Override
    public void beginSearch(final String searchText, final int page, int limit) {
        cancel();
        networkCall = RetrofitManager.getApiService()
                .getMoviesBySearch(searchText, ApiEndpoints.QUERY_TYPE_MOVIE, page, limit);
        networkCall.enqueue(new RetrofitCallback<>(new RequestCallback<ArrayList<SearchedMovie>>() {
            @Override
            public void onResponse(Response<ArrayList<SearchedMovie>> response) {
                ArrayList<SearchedMovie> movies = response == null || response.body() == null ? null : response.body();
                presenter.onSuccess(movies, searchText, page);
            }

            @Override
            public void onFailure(Throwable t) {
                presenter.onFailure();
            }
        }));


    }

    @Override
    public void cancel() {
        if (networkCall != null)
            networkCall.cancel();

    }
}
