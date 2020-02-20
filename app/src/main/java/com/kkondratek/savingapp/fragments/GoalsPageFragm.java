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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kkondratek.savingapp.AddGoalActivity;
import com.kkondratek.savingapp.Goal;
import com.kkondratek.savingapp.GoalAdapter;
import com.kkondratek.savingapp.GoalViewModel;
import com.kkondratek.savingapp.MainActivity;
import com.kkondratek.savingapp.R;

import java.util.List;

public class GoalsPageFragm extends Fragment {

    public static final int ADD_GOAL_REQUEST = 1;
    private GoalViewModel goalViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = (ViewGroup)inflater
                .inflate(R.layout.page_goals, container,false);

                final GoalAdapter goalAdapter = new GoalAdapter();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(goalAdapter);

        goalViewModel = ViewModelProviders.of(this).get(GoalViewModel.class);
        goalViewModel.getAllSavings().observe(this, new Observer<List<Goal>>() {
            @Override
            public void onChanged(List<Goal> goals) {
                goalAdapter.setGoals(goals);
            }
        });

        FloatingActionButton buttonAdd = view.findViewById(R.id.button_add_goal);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddGoalActivity.class);
                startActivityForResult(intent, ADD_GOAL_REQUEST);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_GOAL_REQUEST && resultCode == MainActivity.RESULT_OK) {
            String name = data.getStringExtra(AddGoalActivity.EXTRA_NAME);
            String details = data.getStringExtra(AddGoalActivity.EXTRA_DETAILS);
            String price = data.getStringExtra(AddGoalActivity.EXTRA_PRICE);

            Goal goal = new Goal(name, price, details);
            goalViewModel.insert(goal);

            Toast.makeText(getContext(), "Goal added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Goal not added", Toast.LENGTH_SHORT).show();

        }
    }
}
