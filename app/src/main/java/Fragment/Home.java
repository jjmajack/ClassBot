package Fragment;

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

import Activity.Global;
import Activity.R;

import Adapter.SubjectAdapter;

import java.util.ArrayList;
import java.util.List;

import Model.Subject;
import Model.User;

/**
 * Created by Jackson on 11/11/2017.
 */

public class Home extends Fragment {

    User person;
    Bundle bundle;
    View view;
    List<Subject> subjectList = new ArrayList<>();

    ListView listViewTopic;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle saveInstanceState)
    {
        getActivity().setTitle("Dashboard");
        bundle = saveInstanceState;
        view = inflater.inflate(R.layout.dashboard_activity, container, false);

        listViewTopic = (ListView)view.findViewById(R.id.lstViewTopic);
        final Global global = (Global)getActivity().getApplicationContext();
        person = global.getUser();

        subjectList = global.getSubjectList();

        SubjectAdapter subjectAdapter = new SubjectAdapter(view, subjectList);
        listViewTopic.setAdapter(subjectAdapter);

        listViewTopic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Subject subject = subjectList.get(i);
                SubjectFragment subjectFragment = new SubjectFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                bundle = new Bundle();
                bundle.putParcelable("subject", subject);
                subjectFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.content_frame, subjectFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                global.setSubject(subject);
                getActivity().setTitle(subject.getSubjectName());
            }
        });
        return view;
    }
}
