package com.peuyanaga.classbot.RowView;

import android.view.View;
import android.widget.TextView;

import com.peuyanaga.classbot.R;
import com.peuyanaga.classbot.Model.Answer;

/**
 * Created by DELL on 2020-04-26.
 */

public class AnswerRow {

    private TextView lblanswer = null;
    private TextView lblcounter = null;

    public AnswerRow(final View row)
    {
        lblanswer = (TextView)row.findViewById(R.id.answer);
        lblcounter = (TextView)row.findViewById(R.id.counter);
    }

    public void populateFrom(Answer answer, int counter){
        lblanswer.setText(answer.getAnswer());
        lblcounter.setText((counter + 1) + "");
    }
}
