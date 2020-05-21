package com.peuyanaga.classbot.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import com.peuyanaga.classbot.R;
import com.peuyanaga.classbot.Model.Answer;
import com.peuyanaga.classbot.RowView.AnswerRow;

/**
 * Created by DELL on 2020-04-26.
 */

public class AnswerAdapter extends ArrayAdapter<Answer> {

    List<Answer> answerList = new ArrayList<>();
    public AnswerAdapter(Context context, List<Answer> answerList) {
        super(context, R.layout.answer_row, answerList);
        this.answerList = answerList;
    }

    public View getView(int pos, View convertView, ViewGroup parent)
    {
        View row = convertView;
        AnswerRow answerRow;

        if(row == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(R.layout.answer_row, parent, false);
            answerRow = new AnswerRow(row);
            row.setTag(answerRow);
        } else {
            answerRow = (AnswerRow)row.getTag();
        }
        answerRow.populateFrom(answerList.get(pos), pos);
        return row;
    }
}