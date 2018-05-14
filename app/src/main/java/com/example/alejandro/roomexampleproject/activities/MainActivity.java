package com.example.alejandro.roomexampleproject.activities;

import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.alejandro.roomexampleproject.R;
import com.example.alejandro.roomexampleproject.database.AppDatabase;
import com.example.alejandro.roomexampleproject.fragments.UserInfoFragment;
import com.example.alejandro.roomexampleproject.models.User;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    AppDatabase database;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //database
        database = AppDatabase.getInstance(getApplicationContext());
        database.userDao().insert(new User("Alejandro", "Velasco", "22577777"));

        //setting up the toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        //setting up drawerlayout
        drawerLayout = findViewById(R.id.drawerLayout);

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                UserInfoFragment userInfoFragment = new UserInfoFragment();
                userInfoFragment.setUser(database.userDao().getAll().get(0));
                fragmentTransaction.replace(R.id.contentFrame, userInfoFragment);
                fragmentTransaction.commit();
                return true;
            }
        });

    }
}
