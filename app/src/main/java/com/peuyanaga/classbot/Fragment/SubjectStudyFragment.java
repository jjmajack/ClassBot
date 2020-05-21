package com.peuyanaga.classbot.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;

import com.peuyanaga.classbot.Activity.Global;
import com.peuyanaga.classbot.R;
import com.peuyanaga.classbot.Model.Ranking;
import com.peuyanaga.classbot.Model.Subject;
import com.peuyanaga.classbot.Model.User;

/**
 * Created by Jackson on 11/11/2017.
 */

public class SubjectStudyFragment extends Fragment {

    Subject subject;
    Bundle bundle;
    View view;
    ArrayList<Ranking> rankings = new ArrayList<>();
    WebView webvStudy;
    User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState)
    {
        Global global = (Global)getActivity().getApplicationContext();
        subject = global.getSubject();
        user = global.getUser();
        getActivity().setTitle(subject.getSubjectName() + " - " + "Study");
        bundle = saveInstanceState;
        view = inflater.inflate(R.layout.topic_study_fragment, container, false);

        webvStudy = (WebView) view.findViewById(R.id.webview_study);
        webvStudy.setWebViewClient(new WebClient());
        webvStudy.getSettings().setLoadsImagesAutomatically(true);
        webvStudy.getSettings().setJavaScriptEnabled(true);
        webvStudy.loadUrl(String.format("https://www.schoolsportal.co.za/?gradesubjectid=%d", subject.getGradeSubjectId()));

        return view;
    }
    //
    private class WebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
