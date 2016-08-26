package com.wordpress.laaptu.mvp.movie.base.model;

import android.os.Parcel;
import android.os.Parcelable;


/**
 */
public class Movie implements Parcelable {
    public String title, imdbId, slug;
    public int year;

    protected Movie(Parcel in) {
        title = in.readString();
        imdbId = in.readString();
        slug = in.readString();
        year = in.readInt();
    }

    public Movie() {

    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(imdbId);
        dest.writeString(slug);
        dest.writeInt(year);
    }
}
