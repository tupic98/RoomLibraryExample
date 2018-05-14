package com.example.alejandro.roomexampleproject.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.alejandro.roomexampleproject.R;
import com.example.alejandro.roomexampleproject.database.AppDatabase;
import com.example.alejandro.roomexampleproject.database.daos.NoteDao;
import com.example.alejandro.roomexampleproject.database.daos.UserDao;
import com.example.alejandro.roomexampleproject.fragments.UserInfoFragment;
import com.example.alejandro.roomexampleproject.models.User;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    AppDatabase database;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

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
        new FillInitialDbAsync(database).execute();

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

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();

                switch (item.getItemId()){
                    case R.id.user:
                        new GetUsersAsync(database).execute();
                        break;

                    case R.id.notes:
                        Log.d("DrawerLayout", "Notes not implemented yet");
                        break;
                }
                return true;
            }
        });
    }

    private class GetUsersAsync extends AsyncTask<Void, User, User>{ //This is a thread
        private final UserDao userdao;

        private GetUsersAsync(AppDatabase db) {
            this.userdao = db.userDao();
        }

        @Override
        protected User doInBackground(Void... voids) { //Transaccion de los datos en background
            return userdao.getAll().get(0);
        }

        @Override
        protected void onPostExecute(User user) { //Se corre en el thread principal, no en el Async
            super.onPostExecute(user);
            UserInfoFragment fragment = new UserInfoFragment();
            fragment.setUser(user);

            fragmentTransaction.replace(R.id.contentFrame, fragment);
            fragmentTransaction.commit();
        }
    }

    private class FillInitialDbAsync extends AsyncTask<Void, Void, Void>{
        private final UserDao userdao;
        private final NoteDao notedao;

        private FillInitialDbAsync(AppDatabase db) {
            this.userdao = db.userDao();
            this.notedao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userdao.insert(new User("Alejandro", "Velasco", "22577777"),
                    new User("Enrique", "Palacios", "22577777"));
            return null;
        }
    }

    private class FirstAsyncClass extends  AsyncTask<Void,Void,Void>{//<Params, Progress, Result>, <doInBackground, , onPostExecute>

        @Override
        protected Void doInBackground(Void... voids) { //Unico metodo necesario. Lo que va a pasar en el fondo. Yeah
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) { //Correr en el UIThread. Las referencias ya existen en este metodo, se ejecuta en el main Thread
            super.onPostExecute(aVoid);
        }
    }
 }
