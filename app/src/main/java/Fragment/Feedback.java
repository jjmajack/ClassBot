package Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import Activity.Global;
import Activity.R;

import Model.ServerResponse;
import Model.User;

public class Feedback extends Fragment {

    View view;
    User user;
    Gson gson = new Gson();

    @Nullable
    @Override
     public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState)
    {
        Global global = (Global)getActivity().getApplicationContext();
        getActivity().setTitle("Feedback");
        user = global.getUser();
        view = inflater.inflate(R.layout.feedback_fragment, container, false);

        final TextView title = (TextView)view.findViewById(R.id.title);
        final TextView feedback = (TextView)view.findViewById(R.id.feedback_body);
        final Spinner type = (Spinner)view.findViewById(R.id.type);
        Button submit = (Button)view.findViewById(R.id.submit_feedback);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (title.getText().toString().isEmpty() || feedback.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Please complete the form before you sen a feedback", Toast.LENGTH_LONG).show();
                    return;
                }
                final RequestQueue requestQueue = Volley.newRequestQueue(getContext());

                String url = "http://192.168.8.114:8081/trivia365/api/feedback";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ServerResponse httpResponse = gson.fromJson(response, ServerResponse.class);
                        Toast.makeText(getContext(), httpResponse.getMessage(), Toast.LENGTH_LONG).show();

                        if (httpResponse.getStatus().equalsIgnoreCase("success")){
                            title.setText("");
                            feedback.setText("");
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
                        params.put("title", title.getText().toString());
                        params.put("type", type.getSelectedItem().toString());
                        params.put("feedback", feedback.getText().toString());
                        params.put("userId", user.getUserId() + "");
                        return params;
                    }
                };

                requestQueue.add(stringRequest);
            }
        });

        return view;
    }
}
