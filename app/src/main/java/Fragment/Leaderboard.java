package Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import Activity.R;

import Adapter.LeaderboardAdapter;
import Model.Ranking;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import Model.User;
import RowView.LeaderboardRow;

/**
 * Created by Jackson on 11/18/2017.
 */

public class Leaderboard extends Fragment {

    User person;
    Bundle bundle;
    View view;
    ArrayList<Ranking> rankings = new ArrayList<>();
    ListView lstViewResults;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState)
    {
        //final ProgressDialog pd = ProgressDialog.show(view.getContext().getApplicationContext(), "", "Loading, Please wait...");
        person = this.getArguments().getParcelable("person");
        bundle = saveInstanceState;
        view = inflater.inflate(R.layout.layout_leaderboard, container, false);

        lstViewResults = (ListView)view.findViewById(R.id.lstViewLeaderBoard);

        final RequestQueue requestQueue = Volley.newRequestQueue(view.getContext().getApplicationContext());

        String url = "http://192.168.8.114:8081/trivia365/api/ranking";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                rankings = gson.fromJson(response, new TypeToken<ArrayList<Ranking>>(){}.getType());
                LeaderboardAdapter leaderboardAdapter = new LeaderboardAdapter(view, rankings);

                lstViewResults.setAdapter(leaderboardAdapter);
                leaderboardAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Connection error, Please check your internet and try again", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);
        return view;
    }
}
