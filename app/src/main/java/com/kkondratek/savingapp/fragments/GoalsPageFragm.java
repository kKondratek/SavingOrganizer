package com.kkondratek.savingapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import com.kkondratek.savingapp.R;
import com.kkondratek.savingapp.activities.AddEditGoalActivity;
import com.kkondratek.savingapp.activities.MainActivity;
import com.kkondratek.savingapp.data.Goal;
import com.kkondratek.savingapp.logic.GoalAdapter;
import com.kkondratek.savingapp.logic.GoalViewModel;
import com.kkondratek.savingapp.logic.MainTextViews;
import com.kkondratek.savingapp.logic.UpdateTextEvent;

import java.util.Collections;
import java.util.List;

import de.greenrobot.event.EventBus;

public class GoalsPageFragm extends Fragment {

    private static final int ADD_GOAL_REQUEST = 1;
    private static final int EDIT_GOAL_REQUEST = 2;
    private GoalViewModel goalViewModel;

    private EventBus bus = EventBus.getDefault();

    public GoalsPageFragm() {
    }

    public static GoalsPageFragm newInstance() {
        return new GoalsPageFragm();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater
                .inflate(R.layout.page_goals, container, false);


        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view_goals);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);

        final GoalAdapter goalAdapter = new GoalAdapter();
        recyclerView.setAdapter(goalAdapter);

        goalViewModel = ViewModelProviders.of(this).get(GoalViewModel.class);
        goalViewModel.getAllGoals().observe(getViewLifecycleOwner(), new Observer<List<Goal>>() {
            @Override
            public void onChanged(List<Goal> goals) {
                goalAdapter.setGoals(goals);
                System.out.println("Fragm price");
                bus.post(new UpdateTextEvent(String.valueOf(goalAdapter.getTotalPrice())));
            }
        });

        FloatingActionButton buttonAdd = view.findViewById(R.id.button_add_goal);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddEditGoalActivity.class);
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

        goalAdapter.setOnButtonClickedListener(new GoalAdapter.OnEditButtonClickListener() {
            @Override
            public void onEditButtonClicked(Goal goal) {
                Intent intent = new Intent(getContext(), AddEditGoalActivity.class);
                intent.putExtra(AddEditGoalActivity.EXTRA_ID, goal.getId());
                intent.putExtra(AddEditGoalActivity.EXTRA_NAME, goal.getName());
                intent.putExtra(AddEditGoalActivity.EXTRA_DETAILS, goal.getDetails());
                intent.putExtra(AddEditGoalActivity.EXTRA_PRICE, goal.getPrice());
                startActivityForResult(intent, EDIT_GOAL_REQUEST);
            }
        });

        goalAdapter.setOnButtonClickedListener(new GoalAdapter.OnDoneButtonClickListener() {
            @Override
            public void onDoneButtonClicked(Goal goal) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                int balance = MainActivity.getIntPref("balance", getContext());
                System.out.println("balance: " + balance);
                int price = Integer.parseInt(goalAdapter.getGoalAt(goalAdapter
                        .getFocusedItemPosition()).getPrice());
                if (balance >= price) {
                    balance -= price;
                    MainActivity.putIntPref("balance", balance, getContext());
                    System.out.println(balance + "it works");

                    TextView balanceView = MainTextViews.instance().getBalanceView();
                    String balanceString = String.valueOf(balance);
                    balanceView.setText(balanceString);

                    goalViewModel.delete(goalAdapter.getGoalAt(goalAdapter.getFocusedItemPosition()));
                    Toast.makeText(getContext(), "Goal achieved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "You can't afford it", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_GOAL_REQUEST && resultCode == MainActivity.RESULT_OK) {
            String name = data.getStringExtra(AddEditGoalActivity.EXTRA_NAME);
            String details = data.getStringExtra(AddEditGoalActivity.EXTRA_DETAILS);
            String price = data.getStringExtra(AddEditGoalActivity.EXTRA_PRICE);

            Goal goal = new Goal(name, price, details);
            goalViewModel.insert(goal);

            Toast.makeText(getContext(), "Goal added", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_GOAL_REQUEST && resultCode == MainActivity.RESULT_OK) {

            int id = data.getIntExtra(AddEditGoalActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(getContext(), "Goal can't be created", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = data.getStringExtra(AddEditGoalActivity.EXTRA_NAME);
            String details = data.getStringExtra(AddEditGoalActivity.EXTRA_DETAILS);
            String price = data.getStringExtra(AddEditGoalActivity.EXTRA_PRICE);

            Goal goal = new Goal(name, price, details);
            goal.setId(id);

            goalViewModel.update(goal);

            Toast.makeText(getContext(), "Goal updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Goal not saved", Toast.LENGTH_SHORT).show();
        }
    }
}
