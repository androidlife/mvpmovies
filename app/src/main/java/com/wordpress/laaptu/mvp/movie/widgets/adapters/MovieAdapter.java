package com.wordpress.laaptu.mvp.movie.widgets.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wordpress.laaptu.mvp.movie.R;
import com.wordpress.laaptu.mvp.movie.base.model.Movie;
import com.wordpress.laaptu.mvp.movie.widgets.MovieInfoTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */
public class MovieAdapter extends EndlessAdapter<Movie, MovieAdapter.MovieItemHolder> {


    private LayoutInflater inflater;

    public MovieAdapter(@NonNull Context context, @NonNull List<Movie> items) {
        super(context, items);
        inflater = LayoutInflater.from(context);
    }


    @Override
    protected MovieItemHolder onCreateItemHolder(ViewGroup parent, int viewType) {
        return new MovieItemHolder(inflater.inflate(R.layout.item_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_ITEM)
            ((MovieItemHolder) holder).bind(getItem(position));

    }

    static class MovieItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_movie)
        MovieInfoTextView movieInfo;

        public MovieItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Movie movie) {
            movieInfo.setMovieInfo(movie, null);
        }
    }
}
