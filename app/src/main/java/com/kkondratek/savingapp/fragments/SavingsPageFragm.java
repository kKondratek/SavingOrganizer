package com.kkondratek.savingapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kkondratek.savingapp.activities.AddSavingActivity;
import com.kkondratek.savingapp.activities.MainActivity;
import com.kkondratek.savingapp.R;
import com.kkondratek.savingapp.logic.SavingAdapter;
import com.kkondratek.savingapp.logic.SavingViewModel;
import com.kkondratek.savingapp.data.Saving;

import java.util.List;

public class SavingsPageFragm extends Fragment {

    public static final int ADD_SAVING_REQUEST = 2;

    private SavingViewModel savingViewModel;

    public SavingsPageFragm() {

    }

    public static SavingsPageFragm newInstance() {
        SavingsPageFragm savingsPageFragm= new SavingsPageFragm();
        return savingsPageFragm;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater
                .inflate(R.layout.page_savings, container,false);


        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_savings);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);

        final SavingAdapter savingAdapter = new SavingAdapter();

        recyclerView.setAdapter(savingAdapter);

        savingViewModel = ViewModelProviders.of(this).get(SavingViewModel.class);
        savingViewModel.getAllSavings().observe(this, new Observer<List<Saving>>() {
            @Override
            public void onChanged(List<Saving> savings) {
                savingAdapter.setSavings(savings);

            }
        });

        FloatingActionButton buttonAdd = view.findViewById(R.id.button_add_saving);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddSavingActivity.class);
                startActivityForResult(intent, ADD_SAVING_REQUEST);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                savingViewModel.delete(savingAdapter.getSavingAt(position));
                Toast.makeText(getContext(), "Saving deleted", Toast.LENGTH_LONG).show();

            }
        }).attachToRecyclerView(recyclerView);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_SAVING_REQUEST && resultCode == MainActivity.RESULT_OK) {
            String title = data.getStringExtra(AddSavingActivity.EXTRA_TITLE);
            String amount = data.getStringExtra(AddSavingActivity.EXTRA_AMOUNT);
            int month_day = Integer.parseInt(data.getStringExtra(AddSavingActivity.EXTRA_MONTH_DAY));

            Saving saving = new Saving(title, amount, month_day);
            savingViewModel.insert(saving);

            Toast.makeText(getContext(), "Saving added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Saving not added", Toast.LENGTH_SHORT).show();

        }
    }
}
