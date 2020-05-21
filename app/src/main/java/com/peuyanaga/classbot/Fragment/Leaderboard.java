package com.peuyanaga.classbot.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.peuyanaga.classbot.Activity.Global;
import com.peuyanaga.classbot.R;

import com.peuyanaga.classbot.Adapter.LeaderboardAdapter;
import com.peuyanaga.classbot.Model.Ranking;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import com.peuyanaga.classbot.Model.User;
import com.peuyanaga.classbot.Service.HttpRequest;

/**
 * Created by Jackson on 11/18/2017.
 */

public class Leaderboard extends Fragment {

    User person;
    Bundle bundle;
    Global global;
    View view;
    ArrayList<Ranking> rankings = new ArrayList<>();
    ListView lstViewResults;
    ProgressBar progressBar;
    Gson gson = new Gson();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState)
    {
        global = (Global)getActivity().getApplicationContext();
        person = this.getArguments().getParcelable("person");
        bundle = saveInstanceState;
        view = inflater.inflate(R.layout.layout_leaderboard, container, false);

        lstViewResults = (ListView)view.findViewById(R.id.lstViewLeaderBoard);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);

        new Thread(new Runnable() {
            @Override
            public void run() {

                String url = String.format("%s/ranking", global.getRootURL());

                final String response = new HttpRequest().httpGETRequest(url);

                if(!response.isEmpty()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            rankings = gson.fromJson(response, new TypeToken<ArrayList<Ranking>>() {}.getType());
                            LeaderboardAdapter leaderboardAdapter = new LeaderboardAdapter(view, rankings);

                            lstViewResults.setAdapter(leaderboardAdapter);
                            leaderboardAdapter.notifyDataSetChanged();

                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "Connection error: Please check your internet connection and try again.", Toast.LENGTH_LONG).show();

                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }
        }).start();
        return view;
    }

}
