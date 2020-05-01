package Service;

import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import Adapter.LeaderboardAdapter;
import Model.Ranking;

/**
 * Created by DELL on 2020-04-30.
 */

public class HttpRequest {

    View view;

    public HttpRequest(View view){ this.view = view; }

    public String httpGETRequest(String url){

        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext().getApplicationContext());

        RHandler rH = new RHandler();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, rH, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);

        return rH.getResponse();
    }

    private class RHandler implements Response.Listener<String> {

        String response = "Good";
        @Override
        public void onResponse(String response) {
            this.response = response.toString();
            return;
        }

        public String getResponse(){
            return response;
        }
    }
}
