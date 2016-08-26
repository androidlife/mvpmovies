package com.wordpress.laaptu.mvp.movie;

import android.app.Application;

import timber.log.Timber;

/**
 */
public class MovieApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());
    }
}
