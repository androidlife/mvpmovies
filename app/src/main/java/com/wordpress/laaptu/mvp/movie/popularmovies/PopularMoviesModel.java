package com.wordpress.laaptu.mvp.movie.popularmovies;

import com.wordpress.laaptu.mvp.movie.base.model.Movie;
import com.wordpress.laaptu.mvp.movie.base.network.RequestCallback;
import com.wordpress.laaptu.mvp.movie.base.network.retrofit.RetrofitCallback;
import com.wordpress.laaptu.mvp.movie.base.network.retrofit.RetrofitManager;

import java.util.ArrayList;

import retrofit2.Response;

/**
 */
public class PopularMoviesModel implements PopularMoviesMVP.Model {

    PopularMoviesMVP.Presenter.Model presenter;

    public PopularMoviesModel(final PopularMoviesMVP.Presenter.Model presenter) {
        this.presenter = presenter;

    }

    @Override
    public void fetchPopularMovies(final int page, int limit) {
        RetrofitManager.getApiService().getPopularMovies(page, limit).enqueue(
                new RetrofitCallback<>(new RequestCallback<ArrayList<Movie>>() {
                    @Override
                    public void onResponse(Response<ArrayList<Movie>> response) {
                        ArrayList<Movie> movies = response != null && response.body() != null ? response.body() : null;
                        presenter.onSuccess(movies, page);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        presenter.onFailure();
                    }
                })
        );

    }

    @Override
    public void cancel() {

    }
}
