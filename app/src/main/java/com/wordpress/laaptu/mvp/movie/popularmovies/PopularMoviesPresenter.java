package com.wordpress.laaptu.mvp.movie.popularmovies;

import com.wordpress.laaptu.mvp.movie.base.model.Movie;

import java.util.ArrayList;

/**
 */
public class PopularMoviesPresenter implements PopularMoviesMVP.Presenter.View, PopularMoviesMVP.Presenter.Model {

    private PopularMoviesMVP.View view;
    private PopularMoviesMVP.Model model;

    public PopularMoviesPresenter(PopularMoviesMVP.View view) {
        this.view = view;
        model = new PopularMoviesModel(this);
    }

    @Override
    public void initMovieLoad(int limit) {
        view.showFirstLoad();
        fetchPopularMovies(1, limit);
    }

    @Override
    public void fetchPopularMovies(int page, int limit) {
        view.setLoading(true);
        model.fetchPopularMovies(page, limit);
    }

    @Override
    public void onSuccess(ArrayList<Movie> movies, int page) {
        if (view == null)
            return;
        view.hideProgressErrorIfAny();
        if (movies != null) {
            view.setAdapter(movies, page);
        } else {
            view.setError();
        }
    }

    @Override
    public void onFailure() {
        if (view == null)
            return;
        view.hideProgressErrorIfAny();
        view.setError();
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
