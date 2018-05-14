package com.example.alejandro.roomexampleproject.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.alejandro.roomexampleproject.database.daos.NoteDao;
import com.example.alejandro.roomexampleproject.database.daos.UserDao;
import com.example.alejandro.roomexampleproject.models.Note;
import com.example.alejandro.roomexampleproject.models.User;

@Database(entities = {User.class, Note.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{
    private static final String DB_NAME = "notesDatabase.db";
    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context){
        if (instance == null){
            instance = create(context);
        }

        return instance;
    }

    private static AppDatabase create(Context context) {
        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                DB_NAME
        ).addCallback(roomDatabaseCallback).build();
    }

    private static class PopulateDatabaseAsync extends AsyncTask<Void, Void, Void> {

        private final UserDao userDao;
        private final NoteDao noteDao;

        private PopulateDatabaseAsync(AppDatabase db) {
            this.userDao = db.userDao();
            this.noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            prepareUsers();
            return null;
        }
    }

    private static RoomDatabase.Callback roomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDatabaseAsync(instance).execute();
                }
            };

    public abstract UserDao userDao();
    public abstract NoteDao noteDao();

    protected static void prepareUsers(){
        instance.userDao().insert(new User("Alejandro", "Velasco", "22577777"));
        instance.userDao().insert(new User("Enrique", "Palacios", "22577777"));

        instance.noteDao().insert(new Note("first note, hello world :)", 1));
    }
}
