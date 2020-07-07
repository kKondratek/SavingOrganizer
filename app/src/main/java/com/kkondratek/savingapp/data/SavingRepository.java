package com.kkondratek.savingapp.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class SavingRepository {

    private SavingDao savingDao;

    private LiveData<List<Saving>> savings;

    public SavingRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        savingDao = database.savingDao();
        savings = savingDao.getAllSavings();
    }

    public void insert(Saving saving) {
        new SavingRepository.InsertSavingAsyncTask(savingDao).execute(saving);
    }

    public void update(Saving saving) {
        new SavingRepository.UpdateSavingAsyncTask(savingDao).execute(saving);
    }

    public void delete(Saving saving) {
        new SavingRepository.DeleteSavingAsyncTask(savingDao).execute(saving);
    }

    public void deleteAllSavings() {
        new SavingRepository.DeleteAllSavingsAsyncTask(savingDao).execute();
    }

    public LiveData<List<Saving>> getAllSavings() {
        return savings;
    }

    private static class InsertSavingAsyncTask extends AsyncTask<Saving, Void, Void> {
        private SavingDao savingDao;

        private InsertSavingAsyncTask(SavingDao savingDao) {
            this.savingDao = savingDao;
        }

        @Override
        protected Void doInBackground(Saving... savings) {
            savingDao.insert(savings[0]);
            return null;
        }
    }

    private static class UpdateSavingAsyncTask extends AsyncTask<Saving, Void, Void> {
        private SavingDao savingDao;

        private UpdateSavingAsyncTask(SavingDao savingDao) {
            this.savingDao = savingDao;
        }

        @Override
        protected Void doInBackground(Saving... savings) {
            savingDao.update(savings[0]);
            return null;
        }
    }

    private static class DeleteSavingAsyncTask extends AsyncTask<Saving, Void, Void> {
        private SavingDao savingDao;

        private DeleteSavingAsyncTask(SavingDao savingDao) {
            this.savingDao = savingDao;
        }

        @Override
        protected Void doInBackground(Saving... savings) {
            savingDao.delete(savings[0]);
            return null;
        }
    }

    private static class DeleteAllSavingsAsyncTask extends AsyncTask<Void, Void, Void> {
        private SavingDao savingDao;

        private DeleteAllSavingsAsyncTask(SavingDao savingDao) {
            this.savingDao = savingDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            savingDao.deleteAllSavings();
            return null;
        }
    }
}
