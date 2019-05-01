//Regular StringRequest too slow. Implemented custom request using cookies to increase the speed.

package com.fablix.androidapp;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

public class MyStringRequest extends StringRequest {

    String cookie;

    public MyStringRequest(int method,
                             String url,
                             Listener<String>listener,
                             ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        try {
            this.cookie = getHeaders().get("cookie");
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (MainActivity.localCookie != null && MainActivity.localCookie.length() > 0) {
            HashMap<String, String> headers = new HashMap();
            headers.put("cookie", MainActivity.localCookie);
            return headers;
        }else {
            return super.getHeaders();
        }
    }
}