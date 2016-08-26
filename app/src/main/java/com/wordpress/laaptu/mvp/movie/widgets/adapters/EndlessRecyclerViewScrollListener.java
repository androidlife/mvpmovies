package com.wordpress.laaptu.mvp.movie.widgets.adapters;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import timber.log.Timber;

//https://gist.github.com/nesquena/d09dc68ff07e845cc622
public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 5;

    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;


    RecyclerView.LayoutManager mLayoutManager;
    EndlessAdapter endlessAdapter;


    public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager, EndlessAdapter endlessAdapter, int visibleThreshold) {
        this.mLayoutManager = layoutManager;
        this.endlessAdapter = endlessAdapter;
        this.visibleThreshold = visibleThreshold;
    }


    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        if (dy <= 0) return;
        int totalItemCount = mLayoutManager.getItemCount();
        int lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastCompletelyVisibleItemPosition();


        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            //this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;

        }
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if ((totalItemCount > previousTotalItemCount)) {
            //loading = false;
            previousTotalItemCount = totalItemCount;
            endlessAdapter.setLoadMore(false);
            //++currentPage;
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too
        Timber.d("LastVisibleItem=%d and totalItemCount = %d", lastVisibleItemPosition, totalItemCount);
        if ((lastVisibleItemPosition + visibleThreshold) >= totalItemCount) {
            //currentPage++;
            endlessAdapter.setLoadMore(true);
            onLoadMore(totalItemCount);
            //loading = true;
        }
    }

    // Defines the process for actually loading more data based on page
    public abstract void onLoadMore(int totalItemsCount);

}
