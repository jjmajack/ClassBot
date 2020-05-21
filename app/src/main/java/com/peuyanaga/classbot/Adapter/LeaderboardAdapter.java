package com.peuyanaga.classbot.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.peuyanaga.classbot.R;
import java.util.ArrayList;
import java.util.List;

import com.peuyanaga.classbot.Model.Ranking;
import com.peuyanaga.classbot.RowView.LeaderboardRow;

/**
 * Created by DELL on 2020-04-26.
 */

public class LeaderboardAdapter extends ArrayAdapter<Ranking> {

    List<Ranking> rankings = new ArrayList<>();

    public LeaderboardAdapter(View view, List<Ranking> rankings) {
        super(view.getContext(), R.layout.leaderboard_row, rankings);
        this.rankings = rankings;
    }

    public View getView(int pos, View convertView, ViewGroup parent)
    {
        View row = convertView;
        LeaderboardRow leaderboardRow;

        if(row == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(R.layout.leaderboard_row, parent, false);
            leaderboardRow = new LeaderboardRow(row);
            row.setTag(leaderboardRow);
        } else {
            leaderboardRow = (LeaderboardRow)row.getTag();
        }
        leaderboardRow.populateFrom(rankings.get(pos));
        return row;
    }
}
