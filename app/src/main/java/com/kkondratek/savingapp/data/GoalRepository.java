package com.kkondratek.savingapp.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class GoalRepository {

    private GoalDao goalDao;

    private LiveData<List<Goal>> goals;

    public GoalRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        goalDao = database.goalDao();
        goals = goalDao.getAllGoals();
    }

    public void insert(Goal goal) {
        new InsertGoalAsyncTask(goalDao).execute(goal);
    }


    public void update(Goal goal) {
        new UpdateGoalAsyncTask(goalDao).execute(goal);
    }


    public void delete(Goal goal) {
        new DeleteGoalAsyncTask(goalDao).execute(goal);
    }


    public void deleteAllGoals() {
        new DeleteAllGoalsAsyncTask(goalDao).execute();
    }


    public LiveData<List<Goal>> getAllGoals() {
        return goals;
    }


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

}
