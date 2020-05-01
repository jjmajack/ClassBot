package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import Activity.R;

import java.util.ArrayList;
import java.util.List;

import Model.Subject;
import RowView.SubjectRow;

/**
 * Created by DELL on 2020-04-26.
 */

public class SubjectAdapter extends ArrayAdapter<Subject> {

    List<Subject> subjectList = new ArrayList<>();
    public SubjectAdapter(View view, List<Subject> subjectList) {
        super(view.getContext(), R.layout.dashboard_listgroup, subjectList);
        this.subjectList = subjectList;
    }

    public View getView(int pos, View convertView, ViewGroup parent)
    {
        View row = convertView;
        SubjectRow topicData;

        if(row == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(R.layout.dashboard_listgroup, parent, false);
            topicData = new SubjectRow(row);
            row.setTag(topicData);
        } else {
            topicData = (SubjectRow)row.getTag();
        }
        topicData.populateFrom(subjectList.get(pos));
        return row;
    }
}