package com.wordpress.laaptu.mvp.movie.base.network.retrofit;

import com.wordpress.laaptu.mvp.movie.base.model.Movie;
import com.wordpress.laaptu.mvp.movie.base.model.SearchedMovie;
import com.wordpress.laaptu.mvp.movie.base.network.ApiEndpoints;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 */
public interface RetrofitApiService {

    @GET(ApiEndpoints.URL_POPULAR_MOVIES)
    Call<ArrayList<Movie>> getPopularMovies(
            @Query(ApiEndpoints.QUERY_PAGE) int page,
            @Query(ApiEndpoints.QUERY_LIMIT) int limit
    );

    @GET(ApiEndpoints.URL_SEARCH_MOVIES)
    Call<ArrayList<SearchedMovie>> getMoviesBySearch(
            @Query(ApiEndpoints.QUERY) String query,
            @Query(ApiEndpoints.QUERY_TYPE) String type,
            @Query(ApiEndpoints.QUERY_PAGE) int page,
            @Query(ApiEndpoints.QUERY_LIMIT) int limit
    );
}
