package com.kkondratek.savingapp.logic;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kkondratek.savingapp.data.Saving;
import com.kkondratek.savingapp.data.SavingRepository;

import java.util.List;

public class SavingViewModel extends AndroidViewModel {
    private SavingRepository repository;
    private LiveData<List<Saving>> allSavings;

    public SavingViewModel(@NonNull Application application) {
        super(application);
        repository = new SavingRepository(application);
        allSavings = repository.getAllSavings();
    }

    public void insert(Saving saving) {
        repository.insert(saving);
    }

    public void update(Saving saving) {
        repository.update(saving);
    }

    public void delete(Saving saving) {
        repository.delete(saving);
    }

    public void deleteAllSavings() {
        repository.deleteAllSavings();
    }

    public LiveData<List<Saving>> getAllSavings() {
        return repository.getAllSavings();
    }
}
