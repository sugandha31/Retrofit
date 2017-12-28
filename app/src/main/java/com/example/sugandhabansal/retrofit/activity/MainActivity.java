package com.example.sugandhabansal.retrofit.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.sugandhabansal.retrofit.R;
import com.example.sugandhabansal.retrofit.adapter.MoviesAdapter;
import com.example.sugandhabansal.retrofit.model.Movies;
import com.example.sugandhabansal.retrofit.model.MoviesResponse;
import com.example.sugandhabansal.retrofit.rest.ApiClient;
import com.example.sugandhabansal.retrofit.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private final static String API_KEY = "f955a151c074fce5068dce5dd966171e";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(API_KEY.isEmpty()){
            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            return;
        }

        @SuppressLint("WrongViewCast") final RecyclerView recyclerView = findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<MoviesResponse> call = apiInterface.getTopRatedMovies(API_KEY);

        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                List<Movies> movies = response.body().getResults();
                Log.d(TAG,"Number of movies recieved: "+movies.size());
                recyclerView.setAdapter(new MoviesAdapter(movies,R.layout.list_item_movie,getApplicationContext()));
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.e(TAG,t.toString());
            }
        });

    }
}
