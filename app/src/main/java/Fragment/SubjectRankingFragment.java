package Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import Activity.Global;
import Activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import Adapter.LeaderboardAdapter;
import Model.Ranking;
import Model.Subject;
import Model.User;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState)
    {
        Global global = (Global)getActivity().getApplicationContext();
        subject = global.getSubject();
        user = global.getUser();
        getActivity().setTitle(subject.getSubjectName() + " - " + "Rankings");
        bundle = saveInstanceState;
        view = inflater.inflate(R.layout.topic_ranking_fragment, container, false);

        lstViewResults = (ListView)view.findViewById(R.id.lstView_topic_rankings);

        final RequestQueue requestQueue = Volley.newRequestQueue(view.getContext().getApplicationContext());

        String url = String.format("%s/subject/%d/%d/rankings", global.getRootURL(), subject.getSubjectId(), user.getUserId());
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

            }
        });
        requestQueue.add(stringRequest);

        return view;
    }
}
