package com.example.alejandro.roomexampleproject.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.alejandro.roomexampleproject.database.daos.UserDao;
import com.example.alejandro.roomexampleproject.models.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{
    public abstract UserDao userDao();
}
