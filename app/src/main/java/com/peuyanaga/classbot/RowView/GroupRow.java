package com.peuyanaga.classbot.RowView;

import android.view.View;
import android.widget.TextView;

import com.peuyanaga.classbot.R;
import com.peuyanaga.classbot.Model.Group;

/**
 * Created by DELL on 2020-04-26.
 */

public class GroupRow {

    private TextView counter = null;
    private TextView groupName = null;
    public GroupRow(final View row)
    {
        counter = (TextView)row.findViewById(R.id.lblCountStats);
        groupName = (TextView)row.findViewById(R.id.group_name);
    }

    public void populateFrom(Group group){

        counter.setText(group.getCounter() + "");
        groupName.setText(group.getGroupName());
    }
}
