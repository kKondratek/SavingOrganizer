package com.kkondratek.savingapp;

import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.TextView;

public class BalanceController {

    private SharedPreferences balance;
    private TextView balanceView;

    BalanceController(TextView balanceView, SharedPreferences balance) {
        this.balanceView = balanceView;
        this.balance = balance;
        String balanceStr = balance.getInt("balance", 0) + "PLN";
        balanceView.setText(balanceStr);
    }

    public void addAmount(EditText value) {
        int newBalance;
        if (value.getText().toString().isEmpty()) {
            return;
        } else {
            newBalance = balance
                    .getInt("balance", 0)
                    + Integer.parseInt(value.getText().toString());
        }
            SharedPreferences.Editor editor = balance.edit();
            editor.putInt("balance", newBalance);
            editor.commit();
            String balanceStr = balance.getInt("balance", 0) + "PLN";
            balanceView.setText(balanceStr);

    }

    public void substractAmount(EditText value) {
        int newBalance;
        if (value.getText().toString().isEmpty()) {
            return;
        } else {
            newBalance = balance
                    .getInt("balance", 0)
                    - Integer.parseInt(value.getText().toString());
            if (newBalance < 0) {
                newBalance = 0;
            }
        }
        SharedPreferences.Editor editor = balance.edit();
        editor.putInt("balance", newBalance);
        editor.commit();
        String balanceStr = balance.getInt("balance", 0) + "PLN";
        balanceView.setText(balanceStr);

    }

}
