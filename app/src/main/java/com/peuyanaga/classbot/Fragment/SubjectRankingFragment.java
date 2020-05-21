package com.peuyanaga.classbot.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import com.peuyanaga.classbot.Adapter.LeaderboardAdapter;
import com.peuyanaga.classbot.Model.Ranking;
import com.peuyanaga.classbot.Model.Subject;
import com.peuyanaga.classbot.Model.User;
import com.peuyanaga.classbot.Service.AppService;
import com.peuyanaga.classbot.Service.HttpRequest;

/**
 * Created by Jackson on 11/11/2017.
 */

public class SubjectRankingFragment extends Fragment {

    Subject subject;
    Bundle bundle;
    View view;
    ArrayList<Ranking> rankings = new ArrayList<>();
    ListView lstViewResults;
    User user;
    ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState)
    {
        final Global global = (Global)getActivity().getApplicationContext();
        subject = global.getSubject();
        user = global.getUser();
        getActivity().setTitle(subject.getSubjectName() + " - " + "Rankings");
        bundle = saveInstanceState;
        view = inflater.inflate(R.layout.topic_ranking_fragment, container, false);

        lstViewResults = (ListView)view.findViewById(R.id.lstView_topic_rankings);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = String.format("%s/subject/%d/rankings", global.getRootURL(), subject.getGradeSubjectId());


                final String response = new HttpRequest().httpGETRequest(url);

                if(!response.isEmpty()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Gson gson = new Gson();
                            rankings = gson.fromJson(response, new TypeToken<ArrayList<Ranking>>() {
                            }.getType());
                            LeaderboardAdapter leaderboardAdapter = new LeaderboardAdapter(view, rankings);

                            lstViewResults.setAdapter(leaderboardAdapter);
                            leaderboardAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });

                }else {
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
