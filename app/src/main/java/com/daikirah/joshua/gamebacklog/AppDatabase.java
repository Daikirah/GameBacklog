package com.daikirah.joshua.gamebacklog;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Game.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public  abstract GameDao gameDao();

    private final static String DATABASE_NAME = "game_db";
    private static AppDatabase dbInstance;

    public static AppDatabase getDbInstance(Context context){

        if (dbInstance == null){
            dbInstance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
        }
        return dbInstance;
    }

}
