package com.example.simplechef.ui.home;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.simplechef.RecipeClass;

@Database(entities= RecipeClass.class, version = 1)
public abstract class RecipeDatabase extends RoomDatabase {

    private static RecipeDatabase instance;

    public abstract RecipeDao recipeDao();

    public static synchronized RecipeDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    RecipeDatabase.class, "recipe_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }

    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private RecipeDao recipeDao;

        private PopulateDbAsyncTask(RecipeDatabase db) {
            recipeDao = db.recipeDao();

        }
        @Override
        protected Void doInBackground(Void... voids) {
            recipeDao.insert(new RecipeClass("Robs DAO", "a", "b", "c"));
            return null;
        }
    }
}
