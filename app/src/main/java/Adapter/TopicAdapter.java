package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import Activity.R;
import Model.Subject;
import Model.Topic;
import RowView.SubjectRow;
import RowView.TopicRow;

/**
 * Created by DELL on 2020-04-26.
 */

public class TopicAdapter extends ArrayAdapter<Topic> {

    List<Topic> topicList = new ArrayList<>();
    public TopicAdapter(View view, List<Topic> topicList) {
        super(view.getContext(), R.layout.dashboard_listgroup, topicList);
        this.topicList = topicList;
    }

    public View getView(int pos, View convertView, ViewGroup parent)
    {
        View row = convertView;
        TopicRow topicData;

        if(row == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(R.layout.dashboard_listgroup, parent, false);
            topicData = new TopicRow(row);
            row.setTag(topicData);
        } else {
            topicData = (TopicRow)row.getTag();
        }
        topicData.populateFrom(topicList.get(pos));
        return row;
    }
}