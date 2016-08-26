package com.wordpress.laaptu.mvp.movie.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.wordpress.laaptu.mvp.movie.R;
import com.wordpress.laaptu.mvp.movie.base.BaseActivity;
import com.wordpress.laaptu.mvp.movie.searchmovies.SearchMoviesActivity;

import butterknife.BindView;

public class PopularMoviesActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    public int getLayoutId() {
        return R.layout.activity_popular_movies;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setupToolbar();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, MovieListFragment.getInstance(null), "MovieListFragment").commit();
    }


    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_home);
    }


    private void goToSearchActivity() {
        startActivity(new Intent(this, SearchMoviesActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_popular_movies, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                goToSearchActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
