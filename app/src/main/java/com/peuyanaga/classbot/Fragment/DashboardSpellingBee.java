package com.peuyanaga.classbot.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.peuyanaga.classbot.Activity.Global;
import com.peuyanaga.classbot.Adapter.SubjectAdapter;
import com.peuyanaga.classbot.Model.Subject;
import com.peuyanaga.classbot.Model.User;
import com.peuyanaga.classbot.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackson on 11/11/2017.
 */

public class DashboardSpellingBee extends Fragment {

    User person;
    Bundle bundle;
    View view;
    List<Subject> subjectList = new ArrayList<>();

    ListView listViewTopic;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle saveInstanceState)
    {
        getActivity().setTitle("Spelling Bee");
        bundle = saveInstanceState;
        view = inflater.inflate(R.layout.dashboard_spelling_bee_fragment, container, false);

        listViewTopic = (ListView)view.findViewById(R.id.lstView_subjects);
        final Global global = (Global)getActivity().getApplicationContext();
        person = global.getUser();

        return view;
    }
}
