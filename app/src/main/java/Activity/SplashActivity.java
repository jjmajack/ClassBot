package Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.Timer;

import Model.FromJson;

/**
 * A login screen that offers login via email/password.
 */
public class SplashActivity extends AppCompatActivity {

    ProgressDialog progressDialog;

    FromJson fromJson;
    Global global;
    Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_screen);
        // Set up the login form.

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        global = (Global) getApplicationContext();

        initialize();

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        this.finish();
    }
    public void initialize(){
        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        final String url = String.format("%s/data", global.getRootURL());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                fromJson = gson.fromJson(response, FromJson.class);

                global.setServerList(fromJson.getServerList());
                global.setSchoolList(fromJson.getSchoolList());
                global.setGradeList(fromJson.getGradeList());


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);
    }
}

