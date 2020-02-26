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
import com.kkondratek.savingapp.AddGoalActivity;
import com.kkondratek.savingapp.GoalAdapter;
import com.kkondratek.savingapp.GoalViewModel;
import com.kkondratek.savingapp.MainActivity;
import com.kkondratek.savingapp.R;
import com.kkondratek.savingapp.UpdateTextEvent;
import com.kkondratek.savingapp.data.Goal;

import java.util.Collections;
import java.util.List;

import de.greenrobot.event.EventBus;

public class GoalsPageFragm extends Fragment {

    public static final int ADD_GOAL_REQUEST = 1;
    private GoalViewModel goalViewModel;

    private EventBus bus = EventBus.getDefault();

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater
                .inflate(R.layout.page_goals, container,false);

        final GoalAdapter goalAdapter = new GoalAdapter();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_savings);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(goalAdapter);

        goalViewModel = ViewModelProviders.of(this).get(GoalViewModel.class);
        goalViewModel.getAllGoals().observe(this, new Observer<List<Goal>>() {
            @Override
            public void onChanged(List<Goal> goals) {
                goalAdapter.setGoals(goals);
                bus.post(new UpdateTextEvent(String.valueOf(goalAdapter.getTotalPrice())));
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

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder dragged,
                                  @NonNull RecyclerView.ViewHolder target) {

                int positionDragged = dragged.getAdapterPosition();
                int positionTarget = target.getAdapterPosition();

                Collections.swap(goalViewModel.getAllGoals().getValue(), positionDragged, positionTarget);

                goalAdapter.notifyItemMoved(positionDragged, positionTarget);

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                goalViewModel.delete(goalAdapter.getGoalAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(), "Goal deleted", Toast.LENGTH_LONG).show();

            }
        }).attachToRecyclerView(recyclerView);

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
