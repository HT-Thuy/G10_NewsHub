package com.example.g10_newshub.data.local;

import android.content.Context;
import androidx.room.*;
@Database(entities = {ArticleEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ArticleDao articleDao();
    private static volatile AppDatabase INSTANCE;
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "g10_newshub_database").build();
                }
            }
        }
        return INSTANCE;
    }
}