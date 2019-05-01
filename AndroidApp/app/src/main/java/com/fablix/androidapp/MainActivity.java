package com.fablix.androidapp;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.DefaultRetryPolicy;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.fablix.androidapp.NukeSSLCerts.nuke;


public class MainActivity extends AppCompatActivity {

    public static volatile String localCookie = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        nuke();
        Button button1 = findViewById(R.id.login);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                String url = "https://52.53.154.20:8443/cs122b-project/api/login";
                String email = ((TextView)findViewById(R.id.inputemail)).getText().toString();
                String password = ((TextView)findViewById(R.id.inputpassword)).getText().toString();
                HashMap<String, String> map = new HashMap<>();
                map.put("email", email);
                map.put("password",password);
                map.put("mobile", "mobile");


                MyCustomRequest jsonObjectRequest = new MyCustomRequest(Request.Method.POST, url, map,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                try {
                                    if (jsonObject.getString("status").equals("SUCCESS")) {
                                        Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent();
                                        intent.setClass(MainActivity.this, MovieListActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getBaseContext(),jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                                    }
                                } catch (JSONException e) {
                                    e.getStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(MainActivity.this, "Connection errors", Toast.LENGTH_LONG).show();

                            }
                        }
                ) {
                    @Override
                    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                        Response<JSONObject> superResponse = super.parseNetworkResponse(response);
                        Map<String, String> responseHeaders = response.headers;
                        String rawCookies = responseHeaders.get("Set-Cookie");
                        if (rawCookies != null) {
                            localCookie = rawCookies.substring(0, rawCookies.indexOf(";"));
                        }
                        return superResponse;
                    }
                };
                requestQueue.add(jsonObjectRequest);
            }
        });

    }


}
