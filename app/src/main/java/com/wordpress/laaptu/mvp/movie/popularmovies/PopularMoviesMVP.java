package com.wordpress.laaptu.mvp.movie.popularmovies;

import com.wordpress.laaptu.mvp.movie.base.model.Movie;

import java.util.ArrayList;

/**
 */
public interface PopularMoviesMVP {
    interface Model {
        void fetchPopularMovies(int page, int limit);

        void cancel();
    }

    interface View {
        void showFirstLoad();

        void setError();

        void setAdapter(ArrayList<Movie> movies, int page);

        void hideProgressErrorIfAny();

        void setLoading(boolean isLoading);
    }

    interface Presenter {
        void initMovieLoad(int limit);

        void fetchPopularMovies(int page, int limit);

        void onSuccess(ArrayList<Movie> movies, int page);

        void onFailure();

        void onDestroy();

    }
}
