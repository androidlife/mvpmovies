package com.wordpress.laaptu.mvp.movie.base.network;

import retrofit2.Response;

/**
 */
public interface RequestCallback<T> {
    void onResponse(Response<T> response);

    void onFailure(Throwable t);
}
