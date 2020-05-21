package com.peuyanaga.classbot.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.peuyanaga.classbot.Activity.Global;
import com.peuyanaga.classbot.Activity.GroupMultipleChoice;
import com.peuyanaga.classbot.Activity.GroupQuiz;
import com.peuyanaga.classbot.Adapter.GroupMemberAdapter;
import com.peuyanaga.classbot.R;

import com.peuyanaga.classbot.Adapter.GroupAdapter;
import com.peuyanaga.classbot.Adapter.UserAdapter;
import com.peuyanaga.classbot.Model.Group;
import com.peuyanaga.classbot.Model.ServerResponse;
import com.peuyanaga.classbot.Model.Subject;
import com.peuyanaga.classbot.Model.User;
import com.peuyanaga.classbot.RowView.GroupMemberRow;
import com.peuyanaga.classbot.Service.AppService;
import com.peuyanaga.classbot.Service.HttpRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jackson on 11/11/2017.
 */

public class SubjectGroupFragment extends Fragment {

    Subject subject;
    Bundle bundle;
    View view;
    PopupWindow popupWindow;
    RelativeLayout relativeLayoutGroup, relativeLayout;
    Gson gson = new Gson();
    ArrayList<User> userList = new ArrayList<>();
    List<User> memberList = new ArrayList<>();
    ArrayList<Group> groupList = new ArrayList<>();
    User user;
    LayoutInflater layoutInflater;
    View groupPopupView;
    ListView membersSpinner;
    LinearLayout lstGroupMembers;
    UserAdapter userAdapter;
    Button createGroup;
    GroupAdapter groupAdapter;
    GroupMemberAdapter groupMemberAdapter;
    ListView lstvGroupList;
    EditText groupName;
    EditText searchMembers;
    Boolean isUpdate;
    Global global;
    Group group = new Group();
    ImageButton oneWord, multipleChoice;
    View quizSelectorView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle saveInstanceState)
    {
        global = (Global)getActivity().getApplicationContext();
        subject = global.getSubject();
        user = global.getUser();
        getActivity().setTitle(subject.getSubjectName() + " - " + "Groups");
        bundle = saveInstanceState;
        view = inflater.inflate(R.layout.topic_group_fragment, container, false);

        layoutInflater = LayoutInflater.from(view.getContext());
        groupPopupView = layoutInflater.inflate(R.layout.popup_group, null);
        membersSpinner = (ListView) groupPopupView.findViewById(R.id.members);
        lstGroupMembers = (LinearLayout) groupPopupView.findViewById(R.id.group_members);

        quizSelectorView = layoutInflater.inflate(R.layout.popup_start_quiz, null);
        relativeLayoutGroup = (RelativeLayout)view.findViewById(R.id.topic_groups_fragment_id);

        multipleChoice = (ImageButton) quizSelectorView.findViewById(R.id.multiple_choice);
        oneWord = (ImageButton) quizSelectorView.findViewById(R.id.one_word);

        groupName = (EditText) groupPopupView.findViewById(R.id.group_name);
        searchMembers = (EditText) groupPopupView.findViewById(R.id.membersSearch);
        lstvGroupList = (ListView)view.findViewById(R.id.lstView_topic_groups);
        createGroup = (Button) groupPopupView.findViewById(R.id.create_group);

        groupMemberAdapter = new GroupMemberAdapter(groupPopupView.getContext(), memberList);

        //lstGroupMembers.setAdapter(groupMemberAdapter);

        membersSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View lView, int i, long l) {
                User user = userAdapter.filteredUserList.get(i);

                if (memberList.indexOf(user) == -1) {
                    memberList.add(user);
                } else {
                    memberList.remove(user);
                }
                //groupMemberAdapter.notifyDataSetChanged();
                lstGroupMembers.removeAllViews();
                for(User member : memberList){
                    View cView =  LayoutInflater.from(groupPopupView.getContext()).inflate(R.layout.group_member_row, lstGroupMembers, false);
                    GroupMemberRow row = new GroupMemberRow(cView);
                    row.populateFrom(member);
                    lstGroupMembers.addView(cView);
                }
            }
        });
        searchMembers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                userAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                if(!isUpdate) submitGroup();
                else updateGroup();
                popupWindow.dismiss();
            }
        });

        UserList();
        GroupList();
;

        lstvGroupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Group group = groupList.get(i);
                global.setGroup(group);

                groupName.setText(group.getGroupName());
                memberList.clear();
                createGroup.setText("Update");
                lstGroupMembers.removeAllViews();
                isUpdate = true;

                groupWindow();
            }
        });

        lstvGroupList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                group = groupList.get(i);
                global.setGroup(group);

                quizSelectorPopup();
                return false;
            }
        });
        multipleChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), GroupMultipleChoice.class);
                intent.putExtra("subject", subject);
                intent.putExtra("person", user);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });

        oneWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), GroupQuiz.class);
                intent.putExtra("subject", subject);
                intent.putExtra("person", user);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });
        FloatingActionButton showCreateGroup = (FloatingActionButton)view.findViewById(R.id.create_group);
        relativeLayout = (RelativeLayout)view.findViewById(R.id.topic_groups_fragment_id);

        showCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                isUpdate = false;
                createGroup.setText("Create");
                memberList.clear();
                lstGroupMembers.removeAllViews();
                groupWindow();
            }
        });
        return view;
    }
    //
    private void UserList(){
        final RequestQueue requestQueue = Volley.newRequestQueue(view.getContext().getApplicationContext());

        String url = String.format("%s/user", global.getRootURL(), subject.getSubjectId());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                userList = gson.fromJson(response, new TypeToken<ArrayList<User>>(){}.getType());
                for (User u : userList){
                    if (u.getUserId() == user.getUserId()){
                        userList.remove(u);
                        break;
                    }
                }
                userAdapter = new UserAdapter(groupPopupView, userList);
                membersSpinner.setAdapter(userAdapter);
                membersSpinner.setTextFilterEnabled(true);
                userAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    private void GroupList(){
        final RequestQueue requestQueue = Volley.newRequestQueue(view.getContext().getApplicationContext());

        String url = String.format("%s/subject/%d/%d/groups", global.getRootURL(), subject.getSubjectId(), user.getUserId());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                groupList = gson.fromJson(response, new TypeToken<ArrayList<Group>>(){}.getType());
                //LeaderboardAdapter leaderboardAdapter = new LeaderboardAdapter(view, rankings);

                groupAdapter = new GroupAdapter(view, groupList);
                lstvGroupList.setAdapter(groupAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    public void submitGroup(){
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        String url = String.format("%s/group", global.getRootURL());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                ServerResponse httpResponse = gson.fromJson(response, ServerResponse.class);
                Toast.makeText(getContext(), httpResponse.getMessage(), Toast.LENGTH_LONG).show();

                if (httpResponse.getStatus().equalsIgnoreCase("success")){
                    groupName.setText("");
                    GroupList();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();

                memberList.add(user);

                params.put("groupName", groupName.getText().toString());
                params.put("subjectId", subject.getSubjectId() + "");
                params.put("userId", user.getUserId() + "");
                params.put("members", gson.toJson(memberList));
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    public void groupWindow(){
        popupWindow = new PopupWindow(groupPopupView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        WindowManager wm = (WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        popupWindow.setWidth(width - 60);
        //popupWindow.setHeight(height - 100);

        if(Build.VERSION.SDK_INT >= 21){
            popupWindow.setElevation(5.0f);
        }
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);
        AppService.dimBehind(popupWindow);
    }
    public void updateGroup(){

        String url = String.format("%s/group", global.getRootURL());

        HashMap<String, String> data = new HashMap<>();
        data.put("groupId", group.getGroupId() + "");
        data.put("groupName", groupName.getText().toString());
        data.put("members", gson.toJson(memberList));

        HttpRequest httpRequest = new HttpRequest();
        ServerResponse serverResponse = httpRequest.httpPUTRequest(url, gson.toJson(data));
        GroupList();
    }

    public void quizSelectorPopup(){
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
        popupWindow.showAtLocation(relativeLayoutGroup, Gravity.CENTER, 0, 0);
        AppService.dimBehind(popupWindow);
    }
}
