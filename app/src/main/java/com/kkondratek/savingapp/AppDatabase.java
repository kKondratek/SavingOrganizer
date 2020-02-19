package com.kkondratek.savingapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {Saving.class, Goal.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract GoalDao goalDao();

    public abstract SavingDao savingDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "app_database")
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
        private GoalDao goalDao;
        private SavingDao savingDao;

        private PopulateDbAsyncTask(AppDatabase db) {
            goalDao = db.goalDao();
            savingDao = db.savingDao();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            goalDao.insert(new Goal("Name 1", "100.00",  "some details"));
            goalDao.insert(new Goal("Name 2", "1500.00",  "some details"));

            return null;
        }
    }

}
