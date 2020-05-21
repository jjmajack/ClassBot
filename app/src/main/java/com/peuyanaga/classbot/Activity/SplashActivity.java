package com.peuyanaga.classbot.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.peuyanaga.classbot.R;
import com.peuyanaga.classbot.Model.FromJson;
import com.peuyanaga.classbot.Service.Database;
import com.peuyanaga.classbot.Service.HttpRequest;

import java.util.Timer;

/**
 * A login screen that offers login via email/password.
 */
public class SplashActivity extends AppCompatActivity {

    ProgressDialog progressDialog;

    FromJson fromJson;
    Global global;
    Gson gson = new Gson();
    Database database;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_screen);
        // Set up the login form.

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        global = (Global) getApplicationContext();

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        database = new Database(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                initialize();
                progressBar.setVisibility(View.INVISIBLE);
            }
        }).start();
    }
    public void initialize(){

        HttpRequest httpRequest = new HttpRequest();
        int userId = getUserId();

        if(userId != -1){
            final String url = String.format("%s/user/%d", global.getRootURL(), userId);

            String response = httpRequest.httpGETRequest(url);
            if(!response.isEmpty()) {
                fromJson = gson.fromJson(response, FromJson.class);

                global.setServerList(fromJson.getServerList());
                global.setUser(fromJson.getUser());
                global.setSubjectList(fromJson.getSubjectList());

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("person", fromJson.getUser());
                startActivity(intent);
                finish();
            } else {
                runOnUI();
            }

        } else {
            final String url = String.format("%s/data", global.getRootURL());
            String response = httpRequest.httpGETRequest(url);
            if(!response.isEmpty()) {
                fromJson = gson.fromJson(response, FromJson.class);

                global.setServerList(fromJson.getServerList());
                global.setSchoolList(fromJson.getSchoolList());
                global.setGradeList(fromJson.getGradeList());

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }else {
                runOnUI();
            }
        }

    }
    public void runOnUI(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Connection error: Please check your internet connection and try again.", Toast.LENGTH_LONG).show();
            }
        });
    }
    public int getUserId(){
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.query("USER", new String[]{"userId"}, null, null, null, null, null);
        if(cursor.moveToNext()){
            int userId = cursor.getInt(0);
            db.close();
            return userId;
        } else {
            db.close();
            return -1;
        }
    }

}

