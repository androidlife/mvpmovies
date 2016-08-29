package com.wordpress.laaptu.mvp.movie.searchmovies;

import com.wordpress.laaptu.mvp.movie.base.model.SearchedMovie;

import java.util.ArrayList;

/**
 */
public interface SearchMoviesMVP {

    interface Model {
        void beginSearch(String searchText, int page, int limit);


        void cancel();
    }

    interface View {
        void setAllEmptyNProgress();

        void setAdapter(ArrayList<SearchedMovie> movies, String searchText, int page);

        void setError();

        void hideProgressErrorIfAny();

        void setLoading(boolean isLoading);


    }

    interface Presenter {

        interface View {
            void beginNewSearch(String searchText, int limit);

            void loadMore(String searchText, int page, int limit);

            void onDestroy();
        }

        interface Model {

            void onSuccess(ArrayList<SearchedMovie> movies, String searchText, int page);

            void onFailure();
        }


    }
}
