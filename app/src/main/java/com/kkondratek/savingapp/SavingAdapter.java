package com.kkondratek.savingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.kkondratek.savingapp.data.Saving;

public class SavingAdapter extends ListAdapter<Saving, SavingAdapter.SavingHolder> {

    public SavingAdapter() {
        super(DIFF_CALLBACK);
    }

    private static DiffUtil.ItemCallback<Saving> DIFF_CALLBACK = new DiffUtil.ItemCallback<Saving>() {
        @Override
        public boolean areItemsTheSame(@NonNull Saving oldItem, @NonNull Saving newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Saving oldItem, @NonNull Saving newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getAmount().equals(newItem.getAmount()) &&
                    oldItem.getMonthDay() == newItem.getMonthDay();
        }
    };

    @NonNull
    @Override
    public SavingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.saving_item, parent,false);
        return new SavingHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SavingAdapter.SavingHolder holder, int position) {
        Saving currentSaving = getItem(position);
        String ord;
        int monthDay = currentSaving.getMonthDay();

        switch (monthDay) {
            case 1:
                ord = "st";
                break;
            case 2:
                ord = "nd";
                break;
            case 3:
                ord = "rd";
                break;
            default:
                ord = "sh";
                break;
        }

        String amount = currentSaving.getAmount() + " PLN every " + monthDay + ord;
        holder.textViewTitle.setText(currentSaving.getName());
        holder.textViewAmount.setText(amount);
    }

    public Saving getSavingAt(int position) {
        return getItem(position);
    }

    class SavingHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private TextView textViewAmount;

        public SavingHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewAmount = itemView.findViewById(R.id.text_view_amount);

        }
    }
}
