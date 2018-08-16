package com.example.shola.popularmoviesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.example.shola.popularmoviesapp.MainActivity.EXTRA_MOVIE_IMAGE_URL;
import static com.example.shola.popularmoviesapp.MainActivity.EXTRA_MOVIE_RATING;
import static com.example.shola.popularmoviesapp.MainActivity.EXTRA_MOVIE_RELEASE_DATE;
import static com.example.shola.popularmoviesapp.MainActivity.EXTRA_MOVIE_SUMMARY;
import static com.example.shola.popularmoviesapp.MainActivity.EXTRA_MOVIE_TITLE;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Get intent from MainActivity
        Intent mainMovieIntent = getIntent();
        String movieTitle = mainMovieIntent.getStringExtra(EXTRA_MOVIE_TITLE);
        String movieImageUrl = mainMovieIntent.getStringExtra(EXTRA_MOVIE_IMAGE_URL);
        String moviePlotSummary = mainMovieIntent.getStringExtra(EXTRA_MOVIE_SUMMARY);
        String movieRating = mainMovieIntent.getStringExtra(EXTRA_MOVIE_RATING);
        String movieReleaseDate = mainMovieIntent.getStringExtra(EXTRA_MOVIE_RELEASE_DATE);

        //Value for UI Elements in Detail Activity
        TextView detailMovieTitle = findViewById(R.id.detail_movie_title);
        ImageView detailPosterImage = findViewById(R.id.detail_poster_image);
        TextView detailPlotSummary = findViewById(R.id.plot_summary);
        TextView detailRating = findViewById(R.id.user_rating);
        TextView detailReleaseDate = findViewById(R.id.release_date);

        //Set Values in Detail UI
        detailMovieTitle.setText(movieTitle);
        Picasso.get().load(movieImageUrl).fit().centerCrop().into(detailPosterImage);
        detailPlotSummary.setText(moviePlotSummary);
        detailRating.setText(movieRating);
        detailReleaseDate.setText(movieReleaseDate);


    }
}
