package com.kkondratek.savingapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Update;

import java.util.List;

public class GoalRepository {

    private GoalDao goalDao;
    private SavingDao savingDao;

    private LiveData<List<Goal>> goals;
    private LiveData<List<Saving>> savings;

    public GoalRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        goalDao = database.goalDao();
        savingDao = database.savingDao();

        goals = goalDao.getAllGoals();
        savings = savingDao.getAllSavings();
    }

    public void insert(Goal goal) {
        new InsertGoalAsyncTask(goalDao).execute(goal);
    }

//    public void insert(Saving saving) {
//        new InsertSavingAsyncTask(savingDao).execute(saving);
//    }

    public void update(Goal goal) {
        new UpdateGoalAsyncTask(goalDao).execute(goal);
    }

//    public void update(Saving saving) {
//        new UpdateSavingAsyncTask(savingDao).execute(saving);
//    }

    public void delete(Goal goal) {
        new DeleteGoalAsyncTask(goalDao).execute(goal);
    }

//    public void delete(Saving saving) {
//        new DeleteSavingAsyncTask(savingDao).execute(saving);
//    }

    public void deleteAllGoals() {
        new DeleteAllGoalsAsyncTask(goalDao).execute();
    }

//    public void deleteAllSavings() {
//        new DeleteAllSavingsAsyncTask(savingDao).execute();
//    }

    public LiveData<List<Goal>> getAllGoals() {
        return goals;
    }

//    public LiveData<List<Saving>> getSavings() {
//        return savings;
//    }

    private static class InsertGoalAsyncTask extends AsyncTask<Goal, Void, Void> {
        private GoalDao goalDao;

        private InsertGoalAsyncTask(GoalDao goalDao) {
            this.goalDao = goalDao;
        }
        @Override
        protected Void doInBackground(Goal... goals) {
            goalDao.insert(goals[0]);
            return null;
        }
    }


//    private static class InsertSavingAsyncTask extends AsyncTask<Saving, Void, Void> {
//        private SavingDao savingDao;
//
//        private InsertSavingAsyncTask(SavingDao savingDao) {
//            this.savingDao = savingDao;
//        }
//        @Override
//        protected Void doInBackground(Saving... savings) {
//            savingDao.insert(savings[0]);
//            return null;
//        }
//    }

    private static class UpdateGoalAsyncTask extends AsyncTask<Goal, Void, Void> {
        private GoalDao goalDao;

        private UpdateGoalAsyncTask(GoalDao goalDao) {
            this.goalDao = goalDao;
        }
        @Override
        protected Void doInBackground(Goal... goals) {
            goalDao.update(goals[0]);
            return null;
        }
    }


//    private static class UpdateSavingAsyncTask extends AsyncTask<Saving, Void, Void> {
//        private SavingDao savingDao;
//
//        private UpdateSavingAsyncTask(SavingDao savingDao) {
//            this.savingDao = savingDao;
//        }
//        @Override
//        protected Void doInBackground(Saving... savings) {
//            savingDao.update(savings[0]);
//            return null;
//        }
//    }

    private static class DeleteGoalAsyncTask extends AsyncTask<Goal, Void, Void> {
        private GoalDao goalDao;

        private DeleteGoalAsyncTask(GoalDao goalDao) {
            this.goalDao = goalDao;
        }
        @Override
        protected Void doInBackground(Goal... goals) {
            goalDao.delete(goals[0]);
            return null;
        }
    }


//    private static class DeleteSavingAsyncTask extends AsyncTask<Saving, Void, Void> {
//        private SavingDao savingDao;
//
//        private DeleteSavingAsyncTask(SavingDao savingDao) {
//            this.savingDao = savingDao;
//        }
//        @Override
//        protected Void doInBackground(Saving... savings) {
//            savingDao.delete(savings[0]);
//            return null;
//        }
//    }

    private static class DeleteAllGoalsAsyncTask extends AsyncTask<Void, Void, Void> {
        private GoalDao goalDao;

        private DeleteAllGoalsAsyncTask(GoalDao goalDao) {
            this.goalDao = goalDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            goalDao.deleteAllGoals();
            return null;
        }
    }


//    private static class DeleteAllSavingsAsyncTask extends AsyncTask<Void, Void, Void> {
//        private SavingDao savingDao;
//
//        private DeleteAllSavingsAsyncTask(SavingDao savingDao) {
//            this.savingDao = savingDao;
//        }
//        @Override
//        protected Void doInBackground(Void... voids) {
//            savingDao.deleteAllSavings();
//            return null;
//        }
//    }
}
