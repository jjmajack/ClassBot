package com.peuyanaga.classbot.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.peuyanaga.classbot.Activity.Global;
import com.peuyanaga.classbot.Activity.MultipleChoiceQuiz;
import com.peuyanaga.classbot.R;

import java.util.ArrayList;
import java.util.List;

import com.peuyanaga.classbot.Activity.Quiz;
import com.peuyanaga.classbot.Adapter.TopicAdapter;
import com.peuyanaga.classbot.Model.Subject;
import com.peuyanaga.classbot.Model.Topic;
import com.peuyanaga.classbot.Model.User;
import com.peuyanaga.classbot.Service.AppService;

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
    Topic topic;

    PopupWindow popupWindow;
    RelativeLayout relativeLayoutTopic;
    LayoutInflater layoutInflater;
    ImageButton oneWord, multipleChoice;
    View quizSelectorView;

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

        layoutInflater = LayoutInflater.from(view.getContext());
        quizSelectorView = layoutInflater.inflate(R.layout.popup_start_quiz, null);
        relativeLayoutTopic = (RelativeLayout)view.findViewById(R.id.topit_fragment_id);

        multipleChoice = (ImageButton) quizSelectorView.findViewById(R.id.multiple_choice);
        oneWord = (ImageButton) quizSelectorView.findViewById(R.id.one_word);

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


                topic = topicList.get(i);

                popupWindow = new PopupWindow(quizSelectorView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                WindowManager wm = (WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE);
                Display display = wm.getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;
                int height = size.y;
                popupWindow.setWidth(width - 120);

                if(Build.VERSION.SDK_INT >= 21){
                    popupWindow.setElevation(5.0f);
                }
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.showAtLocation(relativeLayoutTopic, Gravity.CENTER, 0, 0);
               AppService.dimBehind(popupWindow);
            }
        });


        multipleChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MultipleChoiceQuiz.class);
                intent.putExtra("subject", subject);
                intent.putExtra("topic", topic);
                intent.putExtra("person", person);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });

        oneWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Quiz.class);
                intent.putExtra("subject", subject);
                intent.putExtra("topic", topic);
                intent.putExtra("person", person);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });
        return view;
    }

}
