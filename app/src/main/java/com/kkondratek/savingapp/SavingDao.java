package com.kkondratek.savingapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SavingDao {

    @Insert
    void insert(Saving saving);

    @Update
    void update(Saving saving);

    @Delete
    void delete(Saving saving);

    @Query("DELETE FROM savings")
    void deleteAllSavings();

    @Query("SELECT * FROM savings ORDER BY day_of_month DESC")
    LiveData<List<Saving>> getAllSavings();
}
