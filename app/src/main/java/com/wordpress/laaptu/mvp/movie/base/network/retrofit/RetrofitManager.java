package com.wordpress.laaptu.mvp.movie.base.network.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wordpress.laaptu.mvp.movie.BuildConfig;
import com.wordpress.laaptu.mvp.movie.base.network.ApiEndpoints;
import com.wordpress.laaptu.mvp.movie.base.network.gson.GsonAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 */
public class RetrofitManager {
    static volatile Retrofit retrofit;
    private static RetrofitApiService apiService;

    private RetrofitManager() {

    }

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (RetrofitManager.class) {
                if (retrofit == null) {
                    OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
                    okhttpClientBuilder.connectTimeout(1, TimeUnit.MINUTES);
                    okhttpClientBuilder.readTimeout(1, TimeUnit.MINUTES);
                    okhttpClientBuilder.addInterceptor(getHeaderRequestParams());
                    if (BuildConfig.DEBUG)
                        okhttpClientBuilder.addInterceptor(getLoggingInterceptor(HttpLoggingInterceptor.Level.BASIC));
                    Gson gson = new GsonBuilder().registerTypeAdapterFactory(new GsonAdapterFactory()).create();
                    retrofit = new Retrofit.Builder()
                            .baseUrl(ApiEndpoints.URL_BASE)
                            .client(okhttpClientBuilder.build())
                            .addConverterFactory(GsonConverterFactory.create(gson)).build();

                }
            }

        }
        return retrofit;
    }

    private static HttpLoggingInterceptor getLoggingInterceptor(HttpLoggingInterceptor.Level level) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }

    private static Interceptor getHeaderRequestParams() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header(ApiEndpoints.HEADER_CONTENT_TYPE, ApiEndpoints.HEADER_CONTENT_TYPE_JSON)
                        .header(ApiEndpoints.HEADER_TRAKT_API_VERSION, ApiEndpoints.HEADER_TRAKT_API_VERSION_2)
                        .header(ApiEndpoints.HEADER_TRAKT_API_KEY, BuildConfig.TRAKT_API_KEY)
                        .build();
                return chain.proceed(request);
            }
        };
    }

    public static RetrofitApiService getApiService() {
        if (apiService == null) {
            synchronized (RetrofitManager.class) {
                if (apiService == null)
                    apiService = getRetrofit().create(RetrofitApiService.class);
            }
        }
        return apiService;
    }
}
