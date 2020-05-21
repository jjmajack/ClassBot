package com.peuyanaga.classbot.RowView;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.peuyanaga.classbot.Model.Grade;
import com.peuyanaga.classbot.Model.School;
import com.peuyanaga.classbot.Model.User;
import com.peuyanaga.classbot.R;
import com.peuyanaga.classbot.Service.AppService;

/**
 * Created by DELL on 2020-04-26.
 */

public class GroupMemberRow {

    private TextView lblFullname = null;
    public GroupMemberRow(final View row)
    {
        lblFullname = (TextView)row.findViewById(R.id.lblFullNames);
    }

    public void populateFrom(User user){
        lblFullname.setText(user.getFirstname());
    }

}
