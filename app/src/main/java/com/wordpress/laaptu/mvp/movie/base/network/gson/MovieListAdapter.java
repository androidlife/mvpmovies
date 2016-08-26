package com.wordpress.laaptu.mvp.movie.base.network.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.wordpress.laaptu.mvp.movie.base.model.Movie;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 */
public class MovieListAdapter extends TypeAdapter<ArrayList<Movie>> {

    private static final Type MOVIE_LIST_TYPE_TOKEN = new TypeToken<ArrayList<Movie>>() {
    }.getType();

    public static boolean adapts(TypeToken<?> type) {
        return MOVIE_LIST_TYPE_TOKEN.equals(type.getType());
    }

    @Override
    public void write(JsonWriter out, ArrayList<Movie> value) throws IOException {

    }

    @Override
    public ArrayList<Movie> read(JsonReader in) throws IOException {
        ArrayList<Movie> movieList = new ArrayList<>();
        in.beginArray();
        while (in.hasNext()) {
            in.beginObject();
            Movie movie = new Movie();
            readMovie(movie, in);
            movieList.add(movie);
            in.endObject();
        }
        in.endArray();
        return movieList;
    }

    public static void readMovie(Movie movie, JsonReader in) throws IOException {
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "title":
                    movie.title = in.nextString();
                    break;
                case "year":
                    movie.year = in.nextInt();
                    break;
                case "ids":
                    in.beginObject();
                    while (in.hasNext()) {
                        switch (in.nextName()) {
                            case "slug":
                                movie.slug = in.nextString();
                                break;
                            case "imdb":
                                movie.imdbId = in.nextString();
                                break;
                            default:
                                in.skipValue();
                        }
                    }
                    in.endObject();
                    break;
                default:
                    in.skipValue();
            }
        }
    }
}
