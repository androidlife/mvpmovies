package com.wordpress.laaptu.mvp.movie.searchmovies;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.wordpress.laaptu.mvp.movie.R;
import com.wordpress.laaptu.mvp.movie.base.BaseActivity;

import butterknife.BindView;
import timber.log.Timber;

/**
 */
public class SearchMoviesActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    SearchView searchView;

    SearchMoviesFragment searchMoviesFragment;

    private static final String FRAG_TAG = "SearchMoviesFragment";

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_movies;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolbar();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, SearchMoviesFragment.getInstance(null), FRAG_TAG).commit();
        getSupportFragmentManager().executePendingTransactions();
        searchMoviesFragment = (SearchMoviesFragment) getSupportFragmentManager().findFragmentByTag(FRAG_TAG);

    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back);
        getSupportActionBar().setTitle("");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_movies, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        searchMenuItem.expandActionView();
        searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setQueryHint(getString(R.string.search_movies));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                Timber.d("On Query Submit =%s", query);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        performSearch(query);
                    }
                }, 300);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void performSearch(String query) {
        if (searchMoviesFragment != null)
            searchMoviesFragment.searchByMovies(query);

    }

}
