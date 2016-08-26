package com.wordpress.laaptu.mvp.movie;

import com.wordpress.laaptu.mvp.movie.base.model.Movie;
import com.wordpress.laaptu.mvp.movie.base.model.SearchedMovie;
import com.wordpress.laaptu.mvp.movie.base.network.ApiEndpoints;
import com.wordpress.laaptu.mvp.movie.base.network.RequestCallback;
import com.wordpress.laaptu.mvp.movie.base.network.retrofit.RetrofitCallback;
import com.wordpress.laaptu.mvp.movie.base.network.retrofit.RetrofitManager;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

/**
 */
public class RetrofitApiTest {
    @Ignore
    @Test
    public void testPopularMovieFetchApiCall() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        System.out.println("PopularMovieFetchApiCall");
        RetrofitManager.getApiService().getPopularMovies(1, 10).enqueue(
                new RetrofitCallback<>(new RequestCallback<ArrayList<Movie>>() {
                    @Override
                    public void onResponse(Response<ArrayList<Movie>> response) {
                        for (Movie movie : response.body()) {
                            System.out.println("Movie: " + movie.title + " " + movie.year + " " + movie.slug + " " + movie.imdbId);
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        System.out.println("Failure fetching popular movies");
                    }
                })
        );
        latch.await(5, TimeUnit.SECONDS);
    }

    @Ignore
    @Test
    public void testSearchMovieApiCall() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        System.out.println("SearchMovieApiCall()");
        RetrofitManager.getApiService().getMoviesBySearch("iron", ApiEndpoints.QUERY_TYPE_MOVIE, 2, 10)
                .enqueue(
                        new RetrofitCallback<>(new RequestCallback<ArrayList<SearchedMovie>>() {
                            @Override
                            public void onResponse(Response<ArrayList<SearchedMovie>> response) {
                                if (response == null || response.body() == null || response.body().size() == 0) {
                                    System.out.println("Response is null");
                                    return;
                                }
                                for (SearchedMovie movie : response.body()) {
                                    System.out.println("Movie :" + " " + movie.score + " " + movie.title + " " + movie.year + " " + movie.slug + " " + movie.imdbId);
                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                System.out.println("Failure fetching popular movies");
                            }
                        })
                );
        latch.await(7, TimeUnit.SECONDS);

    }

    @Test
    public void testApiCancelCall() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        System.out.println("testApiCancelCall()");
        Call<ArrayList<SearchedMovie>> call = RetrofitManager.getApiService().getMoviesBySearch("iron", ApiEndpoints.QUERY_TYPE_MOVIE, 2, 10);
        call.enqueue(
                new RetrofitCallback<>(new RequestCallback<ArrayList<SearchedMovie>>() {
                    @Override
                    public void onResponse(Response<ArrayList<SearchedMovie>> response) {
                        if (response == null || response.body() == null || response.body().size() == 0) {
                            System.out.println("Response is null");
                            return;
                        }
                        for (SearchedMovie movie : response.body()) {
                            System.out.println("Movie :" + " " + movie.score + " " + movie.title + " " + movie.year + " " + movie.slug + " " + movie.imdbId);
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        System.out.println("Failure fetching popular movies");
                    }
                })
        );
        call.cancel();
        latch.await(7, TimeUnit.SECONDS);

    }
}
