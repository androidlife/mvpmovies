package com.wordpress.laaptu.mvp.movie.base.network;

/**
 */
public class ApiEndpoints {

    public static final String URL_BASE = "https://api.trakt.tv/";

    public static final String URL_POPULAR_MOVIES = "https://api.trakt.tv/movies/popular";
    public static final String URL_SEARCH_MOVIES = "https://api.trakt.tv/search/movie";

    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_CONTENT_TYPE_JSON = "application/json";
    public static final String HEADER_TRAKT_API_VERSION = "trakt-api-version";
    public static final String HEADER_TRAKT_API_VERSION_2 = "2";

    public static final String HEADER_TRAKT_API_KEY = "trakt-api-key";


    public static final String QUERY_PAGE = "page";
    public static final String QUERY_LIMIT = "limit";
    public static final String QUERY = "query";
    public static final String QUERY_TYPE = "type";
    public static final String QUERY_TYPE_MOVIE = "movie";
}
