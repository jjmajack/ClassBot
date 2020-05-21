package com.peuyanaga.classbot.Fragment;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ListView;

import com.peuyanaga.classbot.Activity.Global;
import com.peuyanaga.classbot.Activity.GroupQuiz;
import com.peuyanaga.classbot.Activity.SpellingBeeQuiz;
import com.peuyanaga.classbot.R;

import com.peuyanaga.classbot.Adapter.SubjectAdapter;

import java.util.ArrayList;
import java.util.List;

import com.peuyanaga.classbot.Model.Subject;
import com.peuyanaga.classbot.Model.User;

/**
 * Created by Jackson on 11/11/2017.
 */

public class Dashboard extends Fragment {

    Bundle bundle;
    View view;
    User user;
    BottomNavigationView bottomNavigationView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle saveInstanceState)
    {
        getActivity().setTitle("Subjects");
        bundle = saveInstanceState;

        view = inflater.inflate(R.layout.dashboard_activity, container, false);
        final Global global = (Global)getActivity().getApplicationContext();
        user = global.getUser();

        bottomNavigationView = (BottomNavigationView)view.findViewById(R.id.dashboardNavigationMenu);

        Menu menu =  bottomNavigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.dashboard_subjects);
        menuItem.setChecked(true);

        ItemClickHandler itemClickHandler = new ItemClickHandler();
        itemClickHandler.onNavigationItemSelected(menuItem);
        bottomNavigationView.setOnNavigationItemSelectedListener(itemClickHandler);

        return view;
    }

    private class ItemClickHandler implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.dashboard_subjects:
                    DashboardSubject dashboardFragment = new DashboardSubject();
                    openFragment(dashboardFragment);
                    break;
                case R.id.dashboard_groups:
                    GroupFragment subjectGroupFragment = new GroupFragment();
                    openFragment(subjectGroupFragment);
                    break;
                case R.id.dashboard_spellbee:
                    //DashboardSpellingBee spellingBee = new DashboardSpellingBee();
                    //openFragment(spellingBee);
                    Intent intent = new Intent(getContext(), SpellingBeeQuiz.class);
                    intent.putExtra("person", user);
                    startActivity(intent);
                    break;
            }
            return true;
        }

        //
        private void openFragment(Fragment fragment) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            bundle = new Bundle();
            fragmentTransaction.replace(R.id.dashboad_content, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
