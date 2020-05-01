package RowView;

import android.view.View;
import android.widget.TextView;

import Activity.R;

import Model.Ranking;

/**
 * Created by DELL on 2020-04-26.
 */

public class LeaderboardRow {
    private TextView lblCount = null;
    private TextView lblFullname = null;
    private TextView lblPoints = null;

    public LeaderboardRow(View row){
        lblCount = (TextView)row.findViewById(R.id.lblCountStats);
        lblFullname = (TextView)row.findViewById(R.id.lblFullNames);
        lblPoints = (TextView)row.findViewById(R.id.lblPoints);
    }

    public void populateFrom(Ranking ranking){

        lblCount.setText(ranking.getPosition() + "");
        lblFullname.setText(ranking.getUser().toString());
        lblPoints.setText(ranking.getPoints() + " xp");
    }
}
