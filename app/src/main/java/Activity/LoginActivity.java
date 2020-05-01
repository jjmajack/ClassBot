package Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Model.FromJson;
import com.google.gson.Gson;

import com.android.volley.*;
import  com.android.volley.toolbox.*;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    ProgressDialog progressDialog;

    FromJson fromJson;
    Global global;
    Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        global = (Global)getApplicationContext();
        Button btn = (Button)findViewById(R.id.btnLogin);

        //View v = View.inflate(getApplicationContext(), R.layout.nav_header_main, null);
        final EditText username = (EditText)findViewById(R.id.txtUsername);
        final EditText password = (EditText)findViewById(R.id.txtPassword);

        username.setText("jackson");
        password.setText("12345");

        progressDialog = new ProgressDialog(this);
        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = String.format("%s/user/login/%s/%s", global.getRootURL(), username.getText(), password.getText());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       fromJson = gson.fromJson(response, FromJson.class);
                        if (fromJson.getStatus().equalsIgnoreCase("success")){
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("person", fromJson.getUser());
                            Global global = (Global)getApplicationContext();
                            global.setUser(fromJson.getUser());
                            global.setSubjectList(fromJson.getSubjectList());
                            //Toast.makeText(getApplicationContext(), "Success" + global.getTopicData().length, Toast.LENGTH_LONG).show();
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Connection error, Please check your internet and try again", Toast.LENGTH_LONG).show();
                    }
                });

                requestQueue.add(stringRequest);
            }
        });
    }

    public void Signup(View v)
    {
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(intent);
    }
}

