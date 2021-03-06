package com.peuyanaga.classbot.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.peuyanaga.classbot.Model.FromJson;
import com.peuyanaga.classbot.Model.Grade;
import com.peuyanaga.classbot.Model.ServerResponse;
import com.peuyanaga.classbot.R;
import com.peuyanaga.classbot.Model.School;
import com.google.gson.Gson;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.peuyanaga.classbot.Model.User;

public class SignupActivity extends AppCompatActivity {

    View btnSignup;
    View btnBack;
    EditText username, password, confirm, txtFirstname, txtLastname;
    Spinner spinnerGender, spinnerGrade, spinnerDay, spinnerMonth, spinnerYear;
    SearchableSpinner spinnerSchool;
    String dob = "", day, month, year;

    ProgressDialog progressDialog;
    User person = new User();
    List<School> schoolList = new ArrayList<>();
    List<Grade> gradeList = new ArrayList<>();
    Gson gson = new Gson();
    Global global;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        global = (Global)getApplicationContext();

        context = this;
        btnBack = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.txtPassword);
        confirm = (EditText)findViewById(R.id.txtConfirm);

        txtFirstname = (EditText)findViewById(R.id.txtFirstname);
        txtLastname = (EditText)findViewById(R.id.txtLastname);
        spinnerGender = (Spinner)findViewById(R.id.spinnerGender);
        spinnerSchool = (SearchableSpinner) findViewById(R.id.spinnerSchool);
        spinnerGrade = (Spinner)findViewById(R.id.spinnerGrade);
        spinnerDay = (Spinner)findViewById(R.id.spinnerDay);
        spinnerMonth = (Spinner)findViewById(R.id.spinnerMonth);
        spinnerYear = (Spinner)findViewById(R.id.spinnerYear);

        ButtonHandler btnHandler = new ButtonHandler();
        btnBack.setOnClickListener(btnHandler);
        btnSignup.setOnClickListener(btnHandler);

        TextFieldHandler txtHandler = new TextFieldHandler();
        username.setOnEditorActionListener(txtHandler);
        //password.setOnEditorActionListener(txtHandler);
        //confirm.setOnKeyListener(txtHandler);

        progressDialog = new ProgressDialog(this);

        final List<Integer> yearList = new ArrayList<>();
        for(int x = 2015; x > 1950 ; x--){
            yearList.add(x);
        }
        ArrayAdapter<Integer> yearAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, yearList);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(yearAdapter);

        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                year = yearList.get(i) + "";
                dob = String.format("%s-%s-%s", year, month, day);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                day = spinnerDay.getSelectedItem().toString();
                dob = String.format("%s-%s-%s", year, month, day);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                month = adapterView.getSelectedItem().toString();
                dob = String.format("%s-%s-%s", year, month, day);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<Grade> gradeAdapter = new ArrayAdapter<Grade>(this,android.R.layout.simple_spinner_item, global.getGradeList());
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGrade.setAdapter(gradeAdapter);
        spinnerGrade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                person.setGrade((Grade)adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<School> schoolAdapter = new ArrayAdapter<School>(this,android.R.layout.simple_spinner_item, global.getSchoolList());
        schoolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSchool.setAdapter(schoolAdapter);
        spinnerSchool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                person.setSchool((School)adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public class TextFieldHandler implements TextView.OnEditorActionListener
    {
        public boolean onEditorAction(TextView v, int keyCode, KeyEvent e)
        {
            if(keyCode == EditorInfo.IME_ACTION_NEXT)
            {
                if(v.getId() == R.id.username)
                {
                    if(username.getText().toString().isEmpty()) {
                        username.requestFocus();
                        username.setError("This field cannot be empty!");
                        return true;
                    }else {
                        username.clearFocus();
                        return false;
                    }
                }
            }
            return false;
        }
    }
    public class ButtonHandler implements View.OnClickListener
    {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnLogin: {
                    loginIntent();
                    break;
                }
                case R.id.btnSignup: {
                    final String myUsername = username.getText().toString();
                    final String pass = password.getText().toString();
                    final String pass2 = confirm.getText().toString();
                    final String firstname = txtFirstname.getText().toString();
                    final String lastname = txtLastname.getText().toString();
                    final String gender = spinnerGender.getSelectedItem().toString();

                    if (myUsername.isEmpty() || pass.isEmpty() || pass2.isEmpty() || dob.isEmpty() ||
                            firstname.isEmpty() || lastname.isEmpty() || gender.equalsIgnoreCase("select...")) {
                        Toast.makeText(getApplicationContext(), "Please complete all fields and try again", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (!pass.equals(pass2)) {
                        Toast.makeText(getApplicationContext(), "Password do not match, please try again", Toast.LENGTH_LONG).show();
                        //confirm.requestFocus();
                        //confirm.setError("Password do not match!");
                        return;
                    }
                    person.setUsername(myUsername);
                    person.setPassword(pass);
                    person.setFirstname(firstname);
                    person.setLastname(lastname);
                    person.setGender(gender);
                    person.setDob(dob);

                    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                    String url = String.format("%s/user", global.getRootURL());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            ServerResponse serverResponse = gson.fromJson(response, ServerResponse.class);

                            if (serverResponse.getStatus().equalsIgnoreCase("success")) {
                                Toast.makeText(getApplicationContext(), "You have successfully registered", Toast.LENGTH_LONG).show();
                                loginIntent();
                            } else {
                                username.requestFocus();
                                username.setError(serverResponse.getMessage());
                                Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("username", myUsername);
                            params.put("password", pass);
                            params.put("firstname", firstname);
                            params.put("lastname", lastname);
                            params.put("gender", gender);
                            params.put("dob", person.getDob());
                            params.put("role", "student");
                            params.put("schoolId", person.getSchool().getSchoolId() + "");
                            params.put("gradeId", person.getGrade().getGradeId() + "");
                            return params;
                        }
                    };

                    requestQueue.add(stringRequest);
                    requestQueue.getCache().clear();
                    break;
                }
            }
        }
        public void loginIntent(){
            Intent in = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(in);
            finish();
        }
    }
}
