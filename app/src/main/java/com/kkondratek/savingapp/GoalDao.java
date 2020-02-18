package com.kkondratek.savingapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GoalDao {

    @Insert
    void insert(Goal goal);

    @Update
    void update(Goal goal);

    @Delete
    void delete(Goal goal);

    @Query("DELETE FROM goals")
    void deleteAllGoals();

    @Query("SELECT * FROM goals ORDER BY priority DESC")
    LiveData<List<Goal>> getAllGoals();
}
