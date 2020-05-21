package com.peuyanaga.classbot.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import com.peuyanaga.classbot.Model.User;
import com.peuyanaga.classbot.R;
import com.peuyanaga.classbot.RowView.GroupMemberRow;
import com.peuyanaga.classbot.RowView.UserRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2020-04-26.
 */

public class GroupMemberAdapter extends ArrayAdapter<User> {

    List<User> userList;

    public GroupMemberAdapter(Context context, List<User> userList) {
        super(context, R.layout.group_member_row, userList);
        this.userList = userList;
    }

    public View getView(int pos, View convertView, ViewGroup parent)
    {
        View row = convertView;
        final GroupMemberRow userData;

        if(row == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(R.layout.group_member_row, parent, false);
            userData = new GroupMemberRow(row);
            row.setTag(userData);
        } else {
            userData = (GroupMemberRow)row.getTag();
        }
        userData.populateFrom(userList.get(pos));

        return row;
    }
}