package com.peuyanaga.classbot.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.peuyanaga.classbot.Activity.Global;
import com.peuyanaga.classbot.R;
import com.peuyanaga.classbot.Model.Subject;

/**
 * Created by Jackson on 11/11/2017.
 */

public class SubjectFragment extends Fragment {

    Subject subject;
    Bundle bundle;
    View view;
    BottomNavigationView bottomNavigationView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState)
    {
        Global global = (Global)getActivity().getApplicationContext();
        subject = global.getSubject();
        bundle = saveInstanceState;
        view = inflater.inflate(R.layout.topic_activity, container, false);
        bottomNavigationView = (BottomNavigationView)view.findViewById(R.id.topicNavigationMenu);

        Menu menu =  bottomNavigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.topic_subtopics);
        menuItem.setChecked(true);

        ItemClickHandler itemClickHandler = new ItemClickHandler();
        itemClickHandler.onNavigationItemSelected(menuItem);
        bottomNavigationView.setOnNavigationItemSelectedListener(itemClickHandler);

        return view;
    }

    private class ItemClickHandler implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.topic_subtopics:
                    SubjectTopicFragment topicFragment = new SubjectTopicFragment();
                    openFragment(topicFragment);
                    break;
                /*case R.id.topic_groups:
                    SubjectGroupFragment subjectGroupFragment = new SubjectGroupFragment();
                    openFragment(subjectGroupFragment);
                    break;*/
                case R.id.topic_rankings:
                    SubjectRankingFragment subjectRankingFragment = new SubjectRankingFragment();
                    openFragment(subjectRankingFragment);
                    break;
                case R.id.topic_study:
                    SubjectStudyFragment subjectStudyFragment = new SubjectStudyFragment();
                    openFragment(subjectStudyFragment);
                    break;
            }
            return true;
        }
        //
        private void openFragment(Fragment fragment){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            bundle = new Bundle();
            fragmentTransaction.replace(R.id.topic_content, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        //
    }
}
