package com.wordpress.laaptu.mvp.movie.base.model;

import android.os.Parcel;

/**
 */
public class SearchedMovie extends Movie {
    public double score = 10;

    public SearchedMovie() {

    }

    protected SearchedMovie(Parcel in) {
        super(in);
        score = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeDouble(score);
    }

    @Override
    public int describeContents() {
        return super.describeContents();
    }

    public static final Creator<SearchedMovie> CREATOR = new Creator<SearchedMovie>() {
        @Override
        public SearchedMovie createFromParcel(Parcel in) {
            return new SearchedMovie(in);
        }

        @Override
        public SearchedMovie[] newArray(int size) {
            return new SearchedMovie[size];
        }
    };
}
