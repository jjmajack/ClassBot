package Fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import Activity.R;
import Activity.Global;

import Model.User;

public class Profile extends Fragment {

    @Nullable
    @Override
     public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState)
    {
        Global global = (Global)getActivity().getApplicationContext();
        User person = global.getUser();
        View v = inflater.inflate(R.layout.activity_profile, container, false);

        TextView fullname = (TextView)v.findViewById(R.id.lblPFullname);
        TextView username = (TextView)v.findViewById(R.id.lblPUsername);
        TextView gender = (TextView)v.findViewById(R.id.lblPGender);
        TextView dob = (TextView)v.findViewById(R.id.lblPDOB);
        TextView school = (TextView)v.findViewById(R.id.lblSchool);
        TextView grade = (TextView)v.findViewById(R.id.lblGrade);
        TextView lblInitials = (TextView)v.findViewById(R.id.lblPInitials);

        fullname.setText(person.toString());
        username.setText(person.getUsername());
        gender.setText(person.getGender());
        dob.setText(person.getDob());
        school.setText(person.getSchool() != null? person.getSchool().getSchoolName() : "N/A");
        grade.setText(person.getGrade() != null? person.getGrade().getGrade() : "N/A");
        lblInitials.setText(String.format("%s%s", person.getLastname().charAt(0), person.getFirstname().charAt(0)));
        return v;
    }
}
