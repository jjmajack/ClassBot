package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import Activity.R;
import Model.Group;
import RowView.GroupRow;

/**
 * Created by DELL on 2020-04-26.
 */

public class GroupAdapter extends ArrayAdapter<Group> {

    List<Group> groupList = new ArrayList<>();
    public GroupAdapter(View view, List<Group> groupList) {
        super(view.getContext(), R.layout.group_row, groupList);
        this.groupList = groupList;
    }

    public View getView(int pos, View convertView, ViewGroup parent)
    {
        View row = convertView;
        GroupRow topicRow;

        if(row == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(R.layout.group_row, parent, false);
            topicRow = new GroupRow(row);
            row.setTag(topicRow);
        } else {
            topicRow = (GroupRow)row.getTag();
        }
        topicRow.populateFrom(groupList.get(pos));
        return row;
    }
}