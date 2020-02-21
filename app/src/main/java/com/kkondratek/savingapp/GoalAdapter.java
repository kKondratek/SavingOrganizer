package com.kkondratek.savingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalHolder> {

    private List<Goal> goals = new ArrayList<>();

    @NonNull
    @Override
    public GoalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.goal_item, parent, false);
        return new GoalHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GoalHolder holder, int position) {
        Goal currentGoal = goals.get(position);
        holder.textViewName.setText(currentGoal.getName());
        holder.textViewPrice.setText(currentGoal.getPrice());
        holder.textViewDetails.setText(currentGoal.getDetails());
    }

    @Override
    public int getItemCount() {
        return goals.size();
    }

    public int getTotalPrice() {

        int totalPrice = 0;

        if (goals.size() > 0) {
            for (Goal goal : goals) {
                totalPrice += Integer.parseInt(goal.getPrice());
            }
        }

        return totalPrice;
    }

    public Goal getGoalAt(int position) {
        return goals.get(position);
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
        notifyDataSetChanged();
    }

    class GoalHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewDetails;
        private TextView textViewPrice;

        public GoalHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewDetails = itemView.findViewById(R.id.text_view_details);
            textViewPrice = itemView.findViewById(R.id.text_view_price);
        }
    }
}
