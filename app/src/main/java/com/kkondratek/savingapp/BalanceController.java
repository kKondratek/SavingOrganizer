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

    public void addAmount(EditText editText) {
        int newBalance;
        if (editText.getText().toString().isEmpty()) {
            return;
        } else {
            newBalance = balance
                    .getInt("balance", 0)
                    + Integer.parseInt(editText.getText().toString());
        }
            SharedPreferences.Editor editor = balance.edit();
            editor.putInt("balance", newBalance);
            editor.commit();
            String balanceStr = balance.getInt("balance", 0) + "PLN";
            balanceView.setText(balanceStr);

    }

    public void substractAmount(EditText editText) {
        int newBalance;
        if (editText.getText().toString().isEmpty()) {
            return;
        } else {
            newBalance = balance
                    .getInt("balance", 0)
                    - Integer.parseInt(editText.getText().toString());
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
