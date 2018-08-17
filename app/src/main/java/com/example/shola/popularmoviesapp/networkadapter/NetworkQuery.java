package com.example.shola.popularmoviesapp.networkadapter;

import android.net.Uri;

import com.example.shola.popularmoviesapp.BuildConfig;

/**
 * Created by shola on 8/8/2018.
 */

public class NetworkQuery{

    private static final String movieAPIKey = BuildConfig.ApiKey;


    public String defaultQuery() {
        Uri.Builder movieUriBuilder = new Uri.Builder();
        String movieURL;
        movieUriBuilder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("discover")
                .appendPath("movie")
                .appendQueryParameter("api_key",movieAPIKey);

        movieURL = movieUriBuilder.build().toString();

        return movieURL;
    }

    public String popularMoviesQuery(){
        Uri.Builder movieUriBuilder = new Uri.Builder();
        String movieURL;
        movieUriBuilder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath("popular")
                .appendQueryParameter("api_key",movieAPIKey);

        movieURL = movieUriBuilder.build().toString();
        return movieURL;
    }

    public String topRatedMoviesQuery() {
        Uri.Builder movieUriBuilder = new Uri.Builder();
        String movieURL;
        movieUriBuilder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath("top_rated")
                .appendQueryParameter("api_key",movieAPIKey);

        movieURL = movieUriBuilder.build().toString();
        return movieURL;
    }
}

