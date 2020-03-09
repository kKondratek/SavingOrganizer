package com.kkondratek.savingapp.logic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kkondratek.savingapp.R;
import com.kkondratek.savingapp.data.Goal;

import java.util.ArrayList;
import java.util.List;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalHolder> {

    private List<Goal> goals = new ArrayList<>();
    private int focusedItemPosition;
    private OnEditButtonClickListener editListener;
    private OnDoneButtonClickListener doneListener;

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
        private ImageButton buttonEdit;
        private ImageButton buttonDone;

        public GoalHolder(@NonNull final View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewDetails = itemView.findViewById(R.id.text_view_details);
            textViewPrice = itemView.findViewById(R.id.text_view_price);
            buttonEdit = itemView.findViewById(R.id.image_button_edit);
            buttonDone = itemView.findViewById(R.id.image_button_done);

            buttonEdit.setOnClickListener(new ImageButton.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (editListener != null && position != RecyclerView.NO_POSITION) {
                        editListener.onEditButtonClicked(goals.get(position));
                    }
                }
            });

            buttonDone.setOnClickListener(new ImageButton.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (doneListener != null && position != RecyclerView.NO_POSITION) {
                        doneListener.onDoneButtonClicked(goals.get(position));
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != focusedItemPosition) {
                        focusedItemPosition = position;
                        buttonDone.setVisibility(View.VISIBLE);
                        buttonEdit.setVisibility(View.VISIBLE);
                        textViewDetails.setVisibility(View.VISIBLE);
                    } else {
                        focusedItemPosition = -1;
                        buttonDone.setVisibility(View.GONE);
                        buttonEdit.setVisibility(View.GONE);
                        textViewDetails.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    public int getFocusedItemPosition() {
        return focusedItemPosition;
    }

    public Goal getFocusedItem() {
        return goals.get(getFocusedItemPosition());
    }

    public interface OnEditButtonClickListener {
        void onEditButtonClicked(Goal goal);
    }

    public interface OnDoneButtonClickListener {
        void onDoneButtonClicked(Goal goal);
    }

    public void setOnButtonClickedListener(OnEditButtonClickListener listener) {
        this.editListener = listener;
    }

    public void setOnButtonClickedListener(OnDoneButtonClickListener listener) {
        this.doneListener = listener;
    }

}
