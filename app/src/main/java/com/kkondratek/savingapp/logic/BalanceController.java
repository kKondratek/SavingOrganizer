package com.kkondratek.savingapp.logic;

import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.TextView;

public class BalanceController {

    private SharedPreferences balance;
    private TextView balanceView;

    public BalanceController(TextView balanceView, SharedPreferences balance) {
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
            editor.apply();
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
        editor.apply();
        String balanceStr = balance.getInt("balance", 0) + "PLN";
        balanceView.setText(balanceStr);

    }

}
