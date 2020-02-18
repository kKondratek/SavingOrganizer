package com.kkondratek.savingapp;

import android.content.SharedPreferences;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.math.BigDecimal;

public class BalanceController {

    private SharedPreferences balance;
    TextView balanceView;

    BalanceController(TextView balanceView, SharedPreferences balance) {
        this.balanceView = balanceView;
        this.balance = balance;
        String balanceStr = balance.getInt("balance", 0) + "PLN";
        balanceView.setText(balanceStr);
    }

    public void addAmount(TextInputEditText value) {
        int newBalance;
        if (value.getText() == null) {
            newBalance = 0;
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

    public void substractAmount(TextInputEditText value) {
        int newBalance;
        if (value.getText() == null) {
            newBalance = 0;
        } else {
            newBalance = balance
                    .getInt("balance", 0)
                    - Integer.parseInt(value.getText().toString());
        }
        SharedPreferences.Editor editor = balance.edit();
        editor.putInt("balance", newBalance);
        editor.commit();
        String balanceStr = balance.getInt("balance", 0) + "PLN";
        balanceView.setText(balanceStr);

    }

}
