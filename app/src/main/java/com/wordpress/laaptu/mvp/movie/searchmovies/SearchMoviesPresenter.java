package com.wordpress.laaptu.mvp.movie.searchmovies;

import com.wordpress.laaptu.mvp.movie.base.model.SearchedMovie;

import java.util.ArrayList;

/**
 */
public class SearchMoviesPresenter implements SearchMoviesMVP.Presenter.Model, SearchMoviesMVP.Presenter.View {

    private SearchMoviesMVP.View view;
    private SearchMoviesMVP.Model model;

    public SearchMoviesPresenter(SearchMoviesMVP.View view) {
        this.view = view;
        model = new SearchMoviesModel(this);
    }

    @Override
    public void beginNewSearch(String searchText, int limit) {
        view.setAllEmptyNProgress();
        loadMore(searchText, 1, limit);
    }

    @Override
    public void loadMore(String searchText, int page, int limit) {
        view.setLoading(true);
        model.beginSearch(searchText, page, limit);
    }

    @Override
    public void onSuccess(ArrayList<SearchedMovie> movies, String searchText, int page) {
        view.hideProgressErrorIfAny();
        if (movies == null)
            view.setError();
        else
            view.setAdapter(movies, searchText, page);

    }

    @Override
    public void onFailure() {
        view.hideProgressErrorIfAny();
        view.setError();
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
