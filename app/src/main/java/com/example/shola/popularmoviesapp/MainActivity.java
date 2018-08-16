package com.example.shola.popularmoviesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.shola.popularmoviesapp.moviesclass.Movie;
import com.example.shola.popularmoviesapp.networkadapter.NetworkQuery;
import com.example.shola.popularmoviesapp.networkadapter.NetworkSingleton;
import com.example.shola.popularmoviesapp.viewadapter.MovieAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnItemClickListener {
    //Instantiate Object need display content for moviesAPI
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private ArrayList<Movie> mMovieList;

    // Movie URL
    public static final String baseImageURL = "http://image.tmdb.org/t/p/w185";

    //Instantiate Values as keys to use for Movie Detail
    public static final String EXTRA_MOVIE_SUMMARY = "movieSummary";
    public static final String EXTRA_MOVIE_RELEASE_DATE = "releaseDate";
    public static final String EXTRA_MOVIE_RATING = "movieRating";
    public static final String EXTRA_MOVIE_IMAGE_URL = "imageUrl";
    public static final String EXTRA_MOVIE_TITLE = "movieTitle";


    //Declare Request Queue needed to make calls with volley
    private RequestQueue mRequestQueue;


    //Network Query Constructor
    public String defaultMovieURL = new NetworkQuery().defaultQuery();
    public String popularMovieURL = new NetworkQuery().popularMoviesQuery();
    public String highestRatedURL = new NetworkQuery().topRatedMoviesQuery();

    /* Recycler view set up */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mMovieAdapter);

        mMovieList = new ArrayList<>();
        mRequestQueue = NetworkSingleton.getInstance(this).getRequestQueue();
        fetchMovies(defaultMovieURL);

    }

    // Menu Item
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.sorting_menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.most_popular:
                fetchMovies(popularMovieURL);
                return true;
            case R.id.highest_rating:
                fetchMovies(highestRatedURL);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void fetchMovies(String requestURL) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, requestURL, null, onMoviesLoaded, onMoviesError);
        mRequestQueue.add(request);

    }

    private final Response.Listener<JSONObject> onMoviesLoaded = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            //Parse results
            try {
                //Clear current movies List Before parsing
                mMovieList.clear();
                JSONArray resultsArray = response.getJSONArray("results");

                //Loop through results in array for populating the UI
                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject movieResult = resultsArray.getJSONObject(i);

                    Movie movie = new Movie();

                    //Parse results to Movie Object
                    movie.setTitle(movieResult.getString("title"));
                    movie.setPosterImageURL(baseImageURL.concat((movieResult.getString("poster_path"))));
                    movie.setReleaseDate(formatDate(movieResult.getString("release_date")));
                    movie.setRating(String.valueOf(movieResult.getDouble("vote_average")));
                    movie.setPlotSynopsis(movieResult.getString("overview"));

                    //Add values to movieArrayList
                    mMovieList.add(movie);
                }

                //Bind Movie Date using an Adapter
                mMovieAdapter = new MovieAdapter(MainActivity.this, mMovieList);
                mMovieAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(mMovieAdapter);
                mMovieAdapter.setOnItemClickListener(MainActivity.this);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    private final Response.ErrorListener onMoviesError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
        }
    };

    // Intent to detailed activity
    @Override
    public void onItemClick(int position) {
        Intent movieDetailIntent = new Intent(this, DetailActivity.class);
        Movie selectedMovie = mMovieList.get(position);

        movieDetailIntent.putExtra(EXTRA_MOVIE_TITLE, selectedMovie.getTitle());
        movieDetailIntent.putExtra(EXTRA_MOVIE_IMAGE_URL, selectedMovie.getPosterImageURL());
        movieDetailIntent.putExtra(EXTRA_MOVIE_SUMMARY, selectedMovie.getPlotSynopsis());
        movieDetailIntent.putExtra(EXTRA_MOVIE_RATING, selectedMovie.getRating());
        movieDetailIntent.putExtra(EXTRA_MOVIE_RELEASE_DATE, selectedMovie.getReleaseDate());

        startActivity(movieDetailIntent);

    }

    private String formatDate(String date) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyy-MM-dd");

        String formattedDate;

        try {
            Date currentDate = dateFormatter.parse(date);
            dateFormatter.applyPattern("MMMM dd, yyyy");
            formattedDate = dateFormatter.format(currentDate);

        } catch (ParseException e) {
            formattedDate = "";
            e.printStackTrace();
        }


        return formattedDate;
    }
}



