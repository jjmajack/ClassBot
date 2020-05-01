package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import Activity.R;
import Model.User;
import RowView.UserRow;

/**
 * Created by DELL on 2020-04-26.
 */

public class UserAdapter extends ArrayAdapter<User> {

    List<User> userList = new ArrayList<>();
    List<User> memberList = new ArrayList<>();
    boolean toggle = false;

    public UserAdapter(View view, List<User> userList) {
        super(view.getContext(), R.layout.newgroup_user_row, userList);
        this.userList = userList;
    }

    public View getView(int pos, View convertView, ViewGroup parent)
    {
        View row = convertView;
        final UserRow userData;

        if(row == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(R.layout.newgroup_user_row, parent, false);
            userData = new UserRow(row);
            row.setTag(userData);
        } else {
            userData = (UserRow)row.getTag();
        }
        userData.populateFrom(userList.get(pos));
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle = !toggle;
                userData.setChecked(toggle);
                if (toggle) {
                    memberList.add(userData.getMember());
                } else {
                    memberList.remove(userData.getMember());
                }
            }
        });
        return row;
    }
    //
    public List<User> getMembers(){
        return memberList;
    }
}