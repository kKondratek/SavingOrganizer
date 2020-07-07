package com.kkondratek.savingapp.logic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kkondratek.savingapp.R;
import com.kkondratek.savingapp.data.Saving;

import java.util.ArrayList;
import java.util.List;

public class SavingAdapter extends RecyclerView.Adapter<SavingAdapter.SavingHolder> {

    private List<Saving> savings = new ArrayList<>();

    @NonNull
    @Override
    public SavingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.saving_item, parent, false);
        return new SavingHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SavingAdapter.SavingHolder holder, int position) {
        Saving currentSaving = savings.get(position);
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
                ord = "th";
                break;
        }

        String amount = currentSaving.getAmount() + " PLN every " + monthDay + ord;
        holder.textViewTitle.setText(currentSaving.getName());
        holder.textViewAmount.setText(amount);
    }

    @Override
    public int getItemCount() {
        return savings.size();
    }

    public void setSavings(List<Saving> savings) {
        this.savings = savings;
        notifyDataSetChanged();
    }

    public Saving getSavingAt(int position) {
        return savings.get(position);
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
