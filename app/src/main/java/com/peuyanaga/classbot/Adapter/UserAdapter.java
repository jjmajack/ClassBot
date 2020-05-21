package com.peuyanaga.classbot.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import com.peuyanaga.classbot.R;
import com.peuyanaga.classbot.Model.User;
import com.peuyanaga.classbot.RowView.UserRow;

/**
 * Created by DELL on 2020-04-26.
 */

public class UserAdapter extends ArrayAdapter<User> {

    public List<User> userList = new ArrayList<>(), filteredUserList = new ArrayList<>(), suggestions = new ArrayList<>();
    Context context;

    public UserAdapter(View view, List<User> suserList) {
        super(view.getContext(), R.layout.newgroup_user_row, R.id.members, suserList);
        this.userList = suserList;
        this.filteredUserList = suserList;
        this.context = view.getContext();
    }

    public View getView(final int pos, View convertView, ViewGroup parent) {
        View row = convertView;
        UserRow userData;

        if(row == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(R.layout.newgroup_user_row, parent, false);
            userData = new UserRow(row);
            row.setTag(userData);
        } else {
            userData = (UserRow)row.getTag();
        }
        userData.populateFrom(filteredUserList.get(pos));
        return row;
    }
    @Override
    public int getCount(){
        return filteredUserList.size();
    }
    @Override
    public long getItemId(int pos){
        return pos;
    }
    @Override
    public User getItem(int pos){
        return filteredUserList.get(pos);
    }
    @Override
    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                suggestions = new ArrayList<>();
                suggestions.clear();
                for(User user : userList){
                    if(user.toString().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        suggestions.add(user);
                    }
                }
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                filteredUserList = (ArrayList<User>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}