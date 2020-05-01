package RowView;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import Activity.R;
import Model.Group;
import Model.User;

/**
 * Created by DELL on 2020-04-26.
 */

public class GroupRow {

    private TextView groupName = null;;
    public GroupRow(final View row)
    {
        groupName = (TextView)row.findViewById(R.id.group_name);
    }

    public void populateFrom(Group group){
        groupName.setText(group.getGroupName());
    }
}
