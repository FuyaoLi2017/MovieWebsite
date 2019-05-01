package com.fablix.androidapp;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.Window;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import android.support.v7.widget.RecyclerView;

public class MovieListActivity extends AppCompatActivity implements ClickListener{
    public int NumOfPages=0;
    public int offset;
    boolean lastPage=false;
    ArrayList<String> id;
    private RecyclerView recyclerView;
    private LinkedList<Map<String, Object>> list = new LinkedList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.movielist);

        Button search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offset = 0;
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext(),new HurlStack());
                String input = ((TextView)findViewById(R.id.input)).getText().toString();
                String url = "https://52.53.154.20:8443/cs122b-project/api/fullTextSearch?query=" + input;
                Log.d("url:",url);
                requestQueue.add(display(url));

            }
        });

        Button previous = findViewById(R.id.previous);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (offset == 0) {
                    return;
                }
                offset -= 1;
                Log.d("offset",String.valueOf(offset));
                String inputOffset = String.valueOf(offset * 20);
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                String input = ((TextView)findViewById(R.id.input)).getText().toString();
                String url = "https://52.53.154.20:8443/cs122b-project/api/fullTextSearch?query=" + input+"&offset="+inputOffset;
                requestQueue.add(display(url));
            }
        });

        Button next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastPage) {
                    return;
                }

                offset += 1;
                Log.d("offset",String.valueOf(offset));
                String inputOffset = String.valueOf(offset * 20);
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                String input = ((TextView)findViewById(R.id.input)).getText().toString();
                String url = "https://52.53.154.20:8443/cs122b-project/api/fullTextSearch?query=" + input+"&offset="+inputOffset;
                requestQueue.add(display(url));
            }
        });


    }



    private MyStringRequest display(String url) {

        MyStringRequest jsonObjectRequest = new MyStringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        try {

                            id=new ArrayList<>();
                            list.clear();

                            JSONObject jsonObject = new JSONObject(str);

                            if (jsonObject.getString("status").equals("SUCCESS")) {

                                NumOfPages=Integer.valueOf(jsonObject.getJSONObject("data").getString("numOfPages"));
                                Log.d("NumOfPages",String.valueOf(NumOfPages));
                                JSONArray result=jsonObject.getJSONObject("data").getJSONArray("autoCompleteList");
                                for(int i=0;i<result.length();i++) {
                                    Map<String, Object> listItem = new HashMap();
                                    JSONObject movieList=result.getJSONObject(i).getJSONObject("data");

                                    Log.d("list", movieList.toString());

                                    id.add(movieList.getString("id"));

                                    Log.d("IDs:",id.toString());

                                    listItem.put("id", movieList.getString("id"));
                                    listItem.put("title", movieList.getString("title"));
                                    listItem.put("year", movieList.getString("year"));
                                    listItem.put("director", movieList.getString("director"));
                                    JSONArray genresList = movieList.getJSONArray("genresList");
                                    JSONArray starsList = movieList.getJSONArray("starsList");
                                    LinkedList<String> genres = new LinkedList();
                                    LinkedList<String> stars = new LinkedList();
                                    if (genresList.length() > 3) {
                                        for (int k = 0; k < 2; ++k) {
                                            genres.add(genresList.getJSONObject(k).getString("name"));


                                        }
                                        genres.add("etc");
                                    } else {
                                        for (int k = 0; k < genresList.length(); ++k) {
                                            genres.add(genresList.getJSONObject(k).getString("name"));


                                        }
                                    }
                                    listItem.put("genres", genres);
                                    if (starsList.length() > 4) {
                                        for (int j = 0; j < 3; ++j) {
                                            stars.add(starsList.getJSONObject(j).getString("name"));


                                        }
                                        stars.add("etc");

                                    } else {
                                        for (int j = 0; j < starsList.length(); ++j) {
                                            stars.add(starsList.getJSONObject(j).getString("name"));
                                        }
                                    }

                                    listItem.put("stars", stars);

                                    list.add(listItem);
                                }
                                if(result.length()<20||offset==NumOfPages){
                                    lastPage=true;
                                }

                                changeView();
                                
                            } else {
                                Toast.makeText(getBaseContext(), "Search failed", Toast.LENGTH_LONG).show();
                                Log.d("error:","ERROR");


                            }
                        } catch (JSONException e) {
                            e.getStackTrace();
                            Log.d("error:","JSONERROR");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(MovieListActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();

                    }
                }

               );

        return jsonObjectRequest;
    }


    private void changeView() {
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        MyAdapter adapter = new MyAdapter(list);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);


    }


    @Override
    public void itemClicked(View view, int position) {

        Log.d("position:",Integer.toString(position));



        Intent intent = new Intent(view.getContext(), SingleMovieActivity.class);
        intent.putExtra("ItemPosition", position);
        Log.d("id",id.get(position));
        intent.putExtra("movieId", id.get(position));
        view.getContext().startActivity(intent);


    }


 }