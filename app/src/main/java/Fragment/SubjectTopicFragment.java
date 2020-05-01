package Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import Activity.Global;
import Activity.R;

import java.util.ArrayList;
import java.util.List;

import Activity.Quiz;
import Adapter.SubjectAdapter;
import Adapter.TopicAdapter;
import Model.Subject;
import Model.Topic;
import Model.User;

/**
 * Created by Jackson on 11/11/2017.
 */

public class SubjectTopicFragment extends Fragment {

    Subject subject;
    Bundle bundle;
    View view;
    List<Topic> topicList = new ArrayList<>();
    ListView listViewTopic;
    User person;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState)
    {
        Global global = (Global)getActivity().getApplicationContext();
        subject = global.getSubject();
        person = global.getUser();

        getActivity().setTitle(subject.getSubjectName() + " - " + "Topics");
        bundle = saveInstanceState;
        view = inflater.inflate(R.layout.topic_subtopic_fragment, container, false);

        listViewTopic = (ListView)view.findViewById(R.id.lstView_topic_topics);

        int x = 1;
        for (Topic topic : subject.getTopicList()){
            topic.setOrder(x);
            topicList.add(topic);
            x++;
        }

        TopicAdapter topicAdapter = new TopicAdapter(view, topicList);
        listViewTopic.setAdapter(topicAdapter);

        listViewTopic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Topic topic = topicList.get(i);

                Intent intent = new Intent(getContext(), Quiz.class);
                intent.putExtra("subject", subject);
                intent.putExtra("topic", topic);
                intent.putExtra("person", person);
                startActivity(intent);
            }
        });
        return view;
    }
}
