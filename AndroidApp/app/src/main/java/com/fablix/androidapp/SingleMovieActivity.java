package com.fablix.androidapp;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import android.support.v7.widget.LinearLayoutManager;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
public class SingleMovieActivity extends AppCompatActivity {
    final ArrayList<SingleMovie> movie = new ArrayList<>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_listview);
        context=this;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext(), new HurlStack());
        Intent in = this.getIntent();
        String movieId = in.getStringExtra("movieId");
        String url = "https://52.53.154.20:8443/cs122b-project/api/single-movie?id=" + movieId;
        Log.d("url:", url);
        requestQueue.add(display(url));
        Log.d("movieList:",movie.toString());


    }
        private MyStringRequest display(String url) {
        MyStringRequest jsonObjectRequest = new MyStringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        try {


                            JSONObject jsonObject = new JSONObject(str);
                            Log.d("Json", jsonObject.toString());

                            if (jsonObject.getString("status").equals("SUCCESS")) {

                                JSONObject result = jsonObject.getJSONObject("data");
                                Log.d("list", result.toString());



                                JSONArray genresList = result.getJSONArray("genresList");
                                ArrayList<String> genres=new ArrayList<>();


                                for (int k = 0; k < genresList.length(); ++k) {
                                    genres.add(genresList.getJSONObject(k).getString("name"));


                                }
                                Log.d("genres", genres.toString());


                                JSONArray starsList = result.getJSONArray("starsList");
                                ArrayList<String> stars=new ArrayList<>();
                                for (int k = 0; k < starsList.length(); ++k) {
                                    stars.add(starsList.getJSONObject(k).getString("name"));


                                }

                                Log.d("stars", stars.toString());


                                movie.add(new SingleMovie(result.getString("title"),result.getString("year"),result.getString("director"),genres,stars));



                                changeView();




                            } else {
                                Toast.makeText(getBaseContext(), "Search failed", Toast.LENGTH_LONG).show();
                                Log.d("error:", "ERROR");


                            }
                        } catch (JSONException e) {
                            e.getStackTrace();
                            Log.d("error:", "JSONERROR");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(SingleMovieActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                        Log.d("security.error", volleyError.toString());
                    }
                }

        );
 return jsonObjectRequest;

    }


    private void changeView() {
        SingleViewAdapter adapter = new SingleViewAdapter(movie, context);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
}