package com.wordpress.laaptu.mvp.movie.base.network.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 */
public class GsonAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (MovieListAdapter.adapts(type))
            return (TypeAdapter<T>) new MovieListAdapter();
        if (SearchedMovieListAdapter.adapts(type))
            return (TypeAdapter<T>) new SearchedMovieListAdapter();
        return null;
    }
}
