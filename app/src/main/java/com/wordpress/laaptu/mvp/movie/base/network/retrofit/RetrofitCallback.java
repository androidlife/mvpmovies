package com.wordpress.laaptu.mvp.movie.base.network.retrofit;

import com.wordpress.laaptu.mvp.movie.base.network.RequestCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 */
public class RetrofitCallback<T> implements Callback<T> {

    private final RequestCallback<T> callback;

    public RetrofitCallback(RequestCallback<T> callback) {
        this.callback = callback;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (call.isCanceled())
            return;
        callback.onResponse(response);

    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (call.isCanceled())
            return;
        callback.onFailure(t);

    }
}
