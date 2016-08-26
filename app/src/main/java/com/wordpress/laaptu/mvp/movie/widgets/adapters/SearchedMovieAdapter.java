package com.wordpress.laaptu.mvp.movie.widgets.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wordpress.laaptu.mvp.movie.R;
import com.wordpress.laaptu.mvp.movie.base.model.SearchedMovie;
import com.wordpress.laaptu.mvp.movie.widgets.MovieInfoTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */
public class SearchedMovieAdapter extends EndlessAdapter<SearchedMovie, SearchedMovieAdapter.SearchedMovieItemHolder> {


    private LayoutInflater inflater;

    public SearchedMovieAdapter(@NonNull Context context, @NonNull List<SearchedMovie> items) {
        super(context, items);
        inflater = LayoutInflater.from(context);
    }


    @Override
    protected SearchedMovieItemHolder onCreateItemHolder(ViewGroup parent, int viewType) {
        return new SearchedMovieItemHolder(inflater.inflate(R.layout.item_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_ITEM)
            ((SearchedMovieItemHolder) holder).bind(getItem(position));

    }

    static class SearchedMovieItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_movie)
        MovieInfoTextView movieInfo;

        public SearchedMovieItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(SearchedMovie movie) {
            movieInfo.setMovieInfo(movie, String.valueOf(movie.score));
        }
    }
}
