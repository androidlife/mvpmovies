package com.wordpress.laaptu.mvp.movie.base.network.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.wordpress.laaptu.mvp.movie.base.model.SearchedMovie;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 */
public class SearchedMovieListAdapter extends TypeAdapter<ArrayList<SearchedMovie>> {

    private static final Type SEARCHED_MOVIE_LIST_TYPE_TOKEN = new TypeToken<ArrayList<SearchedMovie>>() {
    }.getType();

    public static boolean adapts(TypeToken<?> type) {
        return SEARCHED_MOVIE_LIST_TYPE_TOKEN.equals(type.getType());
    }

    @Override
    public void write(JsonWriter out, ArrayList<SearchedMovie> value) throws IOException {

    }

    @Override
    public ArrayList<SearchedMovie> read(JsonReader in) throws IOException {
        ArrayList<SearchedMovie> movieList = new ArrayList<>();
        in.beginArray();
        while (in.hasNext()) {
            in.beginObject();
            SearchedMovie movie = new SearchedMovie();
            while (in.hasNext()) {
                switch (in.nextName()) {
                    case "score":
                        movie.score = in.nextDouble();
                        break;
                    case "movie":
                        in.beginObject();
                        MovieListAdapter.readMovie(movie, in);
                        in.endObject();
                        break;
                    default:
                        in.skipValue();
                }
            }
            movieList.add(movie);
            in.endObject();
        }
        in.endArray();
        return movieList;
    }


}
