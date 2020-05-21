package com.peuyanaga.classbot.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.peuyanaga.classbot.Fragment.Dashboard;
import com.peuyanaga.classbot.Model.CategoryHolder;
import com.peuyanaga.classbot.Fragment.Leaderboard;
import com.peuyanaga.classbot.Fragment.Feedback;
import com.peuyanaga.classbot.R;
import java.util.HashMap;

import com.peuyanaga.classbot.Fragment.Profile;
import com.peuyanaga.classbot.Model.User;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView lblInitials;
    TextView lblFullName;
    private User person;
    private NavigationView navigationView;
    private Menu menu;
    private MenuItem menuItem;
    HashMap categoryList = new HashMap();
    ProgressDialog progressDialog;
    CategoryHolder categoryHolder;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        person = getIntent().getParcelableExtra("person");
        setTitle(person.toString());
        progressDialog = new ProgressDialog(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        lblFullName = (TextView)headerView.findViewById(R.id.lblFullname);
        lblInitials = (TextView)headerView.findViewById(R.id.lblInitials);

        menu = navigationView.getMenu();
        menuItem = menu.findItem(R.id.dashboard);
        menuItem.setChecked(true);
        onNavigationItemSelected(menuItem);


        lblFullName.setText(person.toString());
        lblInitials.setText(String.format("%s%s", person.getLastname().charAt(0), person.getFirstname().charAt(0)));
    }

    @Override
    public void onBackPressed() {
        Dashboard dashboard = new Dashboard();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, dashboard)
                .commit();

        setTitle("Dashboard");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Dashboard/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        switch(item.getItemId())
        {
            //String cHolder = hmapList.get(item.getItemId()).toString();
           // Toast.makeText(getApplicationContext(), cHolder, Toast.LENGTH_LONG).show();
            case R.id.dashboard:
                Dashboard dashboard = new Dashboard();

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, dashboard)
                        .commit();

                setTitle("Dashboard");
                break;

            case R.id.profile:
                Profile profile = new Profile();
                bundle = new Bundle();
                bundle.putParcelable("person", person);
                profile.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, profile)
                        .commit();
                setTitle("Profile");
                break;
            case R.id.rankings:
                Leaderboard leaderboard = new Leaderboard();
                bundle = new Bundle();
                bundle.putParcelable("person", person);
                leaderboard.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, leaderboard)
                        .commit();
                setTitle("Rankings");
                break;
            case R.id.feedback:
                Feedback feedback = new Feedback();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, feedback)
                        .commit();
                break;
            default:
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu){

        menu.clear();

        return true;
    }
    private void openFragment(Fragment fragment){

    }
}
