package com.peuyanaga.classbot.RowView;

import android.view.View;
import android.widget.TextView;

import com.peuyanaga.classbot.R;
import com.peuyanaga.classbot.Model.Subject;

/**
 * Created by DELL on 2020-04-26.
 */

public class SubjectRow {

    TextView lblCounter;
    TextView lblTopic;

    public SubjectRow(View row)
    {
        lblCounter = (TextView)row.findViewById(R.id.lblCountStats);
        lblTopic = (TextView)row.findViewById(R.id.lblTopic);
    }

    public void populateFrom(Subject subject){
        lblCounter.setText(subject.getOrder() + "");
        lblTopic.setText(subject.getSubjectName());
    }
}
