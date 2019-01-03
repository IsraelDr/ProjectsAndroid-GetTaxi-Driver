package com.example.srulispc.projectsandroid_driver.controller.controller;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.srulispc.projectsandroid_driver.R;
import com.example.srulispc.projectsandroid_driver.controller.Adapters.RideAdapter;
import com.example.srulispc.projectsandroid_driver.controller.model.backend.BackendFactory;
import com.example.srulispc.projectsandroid_driver.controller.model.backend.Ibackend;
import com.example.srulispc.projectsandroid_driver.controller.model.entities.Ride;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Ibackend backend;

    private RecyclerView recyclerView;
    private RideAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
            }
        });
        fab.hide();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        //-----------------------Show Available Rides From DataBase---------------------
        backend = BackendFactory.getInstance();
        backend.getallrides(new Ibackend.Action<List<Ride>>(){

            @Override
            public void onSuccess(List<Ride> obj) {
                //Parcelable recyclerViewState;
                //recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();

                recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                adapter = new RideAdapter((ArrayList<Ride>) obj);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                if (obj!=null)
                    recyclerView.setAdapter(adapter);
                //adapter.notifyDataSetChanged();
                //recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
            }

            @Override
            public void onFailure(Exception exception) {

            }

            @Override
            public void onProgress(String status, double percent) {

            }
        });

        //---------------Set on-screen click----------------------------------
        RelativeLayout mainLayout = findViewById(R.id.main_layout);
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                Fragment fragment = fm.findFragmentByTag("newFragment");

                if (fragment!=null)
                    fm.beginTransaction().remove(fragment).commit();

            }
        });
        //-------------Set Driver Details In The Drawer-----------------------
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        TextView textview;
        String driverMail = firebaseAuth.getCurrentUser().getEmail();

        textview = (TextView) navigationView.getHeaderView(0).findViewById(R.id.driverMail);
        textview.setText(driverMail);

//        String driverName = "";
//        ArrayList<Driver> driverList = backend.getalldrivers();
//
//        for (Driver driver : driverList) {
//            if (driver.getMail().equals(driverMail))
//                driverName = driver.getFirstName()+ " " + driver.getLastName();
//        }
//
//        textview = (TextView) navigationView.getHeaderView(0).findViewById(R.id.driverName);
//        textview.setText(driverName);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            FragmentManager fm = getFragmentManager();
            Fragment fragment = fm.findFragmentByTag("newFragment");

            if (fragment!=null)
                fm.beginTransaction().remove(fragment).commit();
            else{
                FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                super.onBackPressed();
            }

        }
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
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_availableRides) {

        } else if (id == R.id.nav_myRides) {

        } else if (id == R.id.nav_exit) {
            FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
            firebaseAuth.signOut();

            super.onBackPressed();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
