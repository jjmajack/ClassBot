package com.peuyanaga.classbot.RowView;

import android.view.View;
import android.widget.TextView;

import com.peuyanaga.classbot.R;
import com.peuyanaga.classbot.Model.Topic;

/**
 * Created by DELL on 2020-04-26.
 */

public class TopicRow {

    TextView lblCounter;
    TextView lblTopic;

    public TopicRow(View row)
    {
        lblCounter = (TextView)row.findViewById(R.id.lblCountStats);
        lblTopic = (TextView)row.findViewById(R.id.lblTopic);
    }

    public void populateFrom(Topic topic){
        lblCounter.setText(topic.getOrder() + "");
        lblTopic.setText(topic.getTopic());
    }
}
