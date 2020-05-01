package Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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

import Activity.Global;
import Activity.GroupQuiz;
import Activity.R;

import Adapter.GroupAdapter;
import Adapter.UserAdapter;
import Model.Group;
import Model.ServerResponse;
import Model.Subject;
import Model.User;

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
    RelativeLayout relativeLayout;
    Gson gson = new Gson();
    ArrayList<User> userList = new ArrayList<>();
    List<User> memberList = new ArrayList<>();
    ArrayList<Group> groupList = new ArrayList<>();
    User user;
    LayoutInflater layoutInflater;
    View groupPopup;
    ListView membersSpinner;
    UserAdapter userAdapter;
    Button createGroup;
    GroupAdapter groupAdapter;
    ListView lstvGroupList;
    EditText groupName;
    EditText searchMembers;
    Global global;


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
        groupPopup = layoutInflater.inflate(R.layout.popup_group, null);
        membersSpinner = (ListView)groupPopup.findViewById(R.id.members);
        groupName = (EditText)groupPopup.findViewById(R.id.group_name);
        searchMembers = (EditText) groupPopup.findViewById(R.id.membersSearch);
        lstvGroupList = (ListView)view.findViewById(R.id.lstView_topic_groups);
        createGroup = (Button)groupPopup.findViewById(R.id.create_group);

        searchMembers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                userAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                submitGroup();
                //Toast.makeText(getContext(), gson.toJson(userAdapter.getMembers()), Toast.LENGTH_LONG).show();

                popupWindow.dismiss();
            }
        });

        UserList();
        GroupList();

        lstvGroupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Group group = groupList.get(i);
                global.setGroup(group);
                Intent intent = new Intent(getContext(), GroupQuiz.class);
                intent.putExtra("subject", subject);
                intent.putExtra("person", user);
                intent.putExtra("port", "12345");
                intent.putExtra("ipaddress", "192.168.8.100");
                startActivity(intent);
            }
        });

        FloatingActionButton createGroup = (FloatingActionButton)view.findViewById(R.id.create_group);
        relativeLayout = (RelativeLayout)view.findViewById(R.id.topic_groups_fragment_id);

        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {

                popupWindow = new PopupWindow(groupPopup, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                if(Build.VERSION.SDK_INT >= 21){
                    popupWindow.setElevation(5.0f);
                }

                view.setBackground(new ColorDrawable(Color.DKGRAY));
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        view.setBackground(new ColorDrawable(Color.WHITE));
                    }
                });
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
                userAdapter = new UserAdapter(groupPopup, userList);
                membersSpinner.setAdapter(userAdapter);
                membersSpinner.setTextFilterEnabled(true);
                userAdapter.notifyDataSetChanged();
                //Toast.makeText(getContext(), userList.size() + "", Toast.LENGTH_LONG).show();
                //lstViewResults.setAdapter(leaderboardAdapter);
                //leaderboardAdapter.notifyDataSetChanged();

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

                //Toast.makeText(getContext(), userList.size() + "", Toast.LENGTH_LONG).show();
                //lstViewResults.setAdapter(leaderboardAdapter);
                //leaderboardAdapter.notifyDataSetChanged();

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

                memberList = userAdapter.getMembers();
                memberList.add(user);

                params.put("groupName", groupName.getText().toString());
                params.put("subjectId", subject.getSubjectId() + "");
                params.put("userId", user.getUserId() + "");
                params.put("members", gson.toJson(userAdapter.getMembers()));
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
