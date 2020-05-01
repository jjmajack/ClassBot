package RowView;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import Activity.R;
import Model.Grade;
import Model.School;
import Model.User;

/**
 * Created by DELL on 2020-04-26.
 */

public class UserRow {

    private TextView lblFullname = null;
    private TextView school = null;
    private TextView grade = null;
    private CheckBox chkMember = null;
    private User member;
    public UserRow(final View row)
    {
        lblFullname = (TextView)row.findViewById(R.id.lblFullNames);
        school = (TextView)row.findViewById(R.id.school);
        grade = (TextView)row.findViewById(R.id.grade);
        chkMember = (CheckBox) row.findViewById(R.id.chkMember);

        chkMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row.performClick();
            }
        });
    }

    public void populateFrom(User user){
        member = user;
        School s = user.getSchool();
        Grade g = user.getGrade();

        lblFullname.setText(user.toString());
        school.setText(s != null? s.toString() : "School: N/A");
        grade.setText(g != null? g.toString() : "Grade : N/A");
    }
    public void setChecked(boolean toggle){
        chkMember.setChecked(toggle);
    }
    //
    public User getMember(){
        return member;
    }
}
