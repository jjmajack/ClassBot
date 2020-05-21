package com.peuyanaga.classbot.RowView;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.peuyanaga.classbot.R;
import com.peuyanaga.classbot.Model.Grade;
import com.peuyanaga.classbot.Model.School;
import com.peuyanaga.classbot.Model.User;
import com.peuyanaga.classbot.Service.AppService;

/**
 * Created by DELL on 2020-04-26.
 */

public class UserRow {

    private TextView lblFullname = null;
    private TextView school = null;
    private TextView grade = null;

    public UserRow(final View row)
    {
        lblFullname = (TextView)row.findViewById(R.id.lblFullNames);
        school = (TextView)row.findViewById(R.id.school);
        grade = (TextView)row.findViewById(R.id.grade);
    }

    public void populateFrom(User user){
        School s = user.getSchool();
        Grade g = user.getGrade();

        lblFullname.setText(user.toString());
        school.setText(s != null? AppService.toUpperCaseWords(s.toString()) : "School: N/A");
        grade.setText(g != null? g.toString() : "Grade : N/A");
    }
}
