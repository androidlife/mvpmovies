package com.wordpress.laaptu.mvp.movie.widgets;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.wordpress.laaptu.mvp.movie.R;
import com.wordpress.laaptu.mvp.movie.base.model.Movie;

/**
 */
public class MovieInfoTextView extends TextView {

    private TextAppearanceSpan title, year, info;
    private SpannableStringBuilder ss;

    public MovieInfoTextView(Context context) {
        this(context, null);
    }

    public MovieInfoTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MovieInfoTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTextAppearanceSpans(context);
    }

    private void initTextAppearanceSpans(Context context) {
        title = new TextAppearanceSpan(context, R.style.AppTheme_TextView_Title);
        year = new TextAppearanceSpan(context, R.style.AppTheme_TextView_Year);
        info = new TextAppearanceSpan(context, R.style.AppTheme_TextView_Title_Small);
    }

    public void setMovieInfo(Movie movie, String score) {
        if (movie == null)
            return;
        if (ss == null)
            ss = new SpannableStringBuilder();
        else
            ss.delete(0, ss.length());

        int startIndex = 0;
        ss.append(movie.title.concat("\n"));
        ss.setSpan(title, startIndex, movie.title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        startIndex = ss.length();
        ss.append(String.valueOf(movie.year).concat("\n\n"));
        ss.setSpan(year, startIndex, ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        startIndex = ss.length();
        String arrow = "   " + getContext().getString(R.string.arrow) + "  ";
        ss.append(getContext().getString(R.string.slug).concat(arrow).concat(movie.slug).concat("\n"));
        arrow = "  " + getContext().getString(R.string.arrow) + "  ";
        ss.append(getContext().getString(R.string.imdb).concat(arrow).concat(movie.imdbId).concat("\n"));
        if (score != null) {
            arrow = " " + getContext().getString(R.string.arrow) + "  ";
            ss.append(getContext().getString(R.string.score).concat(arrow).concat(score).concat("\n"));
        }
        ss.setSpan(info, startIndex, ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(ss);

    }
}
