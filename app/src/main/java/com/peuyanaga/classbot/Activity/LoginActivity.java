package com.peuyanaga.classbot.Activity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.peuyanaga.classbot.Model.FromJson;
import com.peuyanaga.classbot.Model.ServerResponse;
import com.peuyanaga.classbot.Service.Database;

import com.google.gson.Gson;
import com.peuyanaga.classbot.R;

import com.android.volley.*;
import  com.android.volley.toolbox.*;
import com.peuyanaga.classbot.Service.HttpRequest;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    ProgressDialog progressDialog;

    FromJson fromJson;
    Global global;
    Gson gson = new Gson();
    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        global = (Global)getApplicationContext();
        Button btn = (Button)findViewById(R.id.btnLogin);
        database = new Database(this);
        //View v = View.inflate(getApplicationContext(), R.layout.nav_header_main, null);
        final EditText username = (EditText)findViewById(R.id.txtUsername);
        final EditText password = (EditText)findViewById(R.id.txtPassword);

        //username.setText("jackson");
        //password.setText("12345");

        progressDialog = new ProgressDialog(this);
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpRequest httpRequest = new HttpRequest();
                String url = String.format("%s/user/login/%s/%s", global.getRootURL(), username.getText(), password.getText());
                String response = httpRequest.httpGETRequest(url);
                if(!response.isEmpty()){
                   fromJson = gson.fromJson(response, FromJson.class);
                    if (fromJson.getStatus().equalsIgnoreCase("success")){
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("person", fromJson.getUser());
                        Global global = (Global)getApplicationContext();
                        global.setUser(fromJson.getUser());
                        global.setSubjectList(fromJson.getSubjectList());
                        insertData(fromJson.getUser().getUserId());
                        //Toast.makeText(getApplicationContext(), "Success" + global.getTopicData().length, Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Connection error, Please check your internet and try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void insertData(int userId){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userId", userId);
        db.insert("USER", null, contentValues);
        db.close();
    }

    public void Signup(View v)
    {
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(intent);
    }
}

