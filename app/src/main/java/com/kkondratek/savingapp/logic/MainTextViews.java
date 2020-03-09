package com.kkondratek.savingapp.logic;

import android.widget.TextView;

public class MainTextViews {

    private TextView balanceView;
    private TextView priceView;
    private static MainTextViews instance;

    private MainTextViews () {
    }

    public static MainTextViews instance() {
        if (instance == null) {
            instance = new MainTextViews();
       }
        return instance;
    }

    public void setBalanceView(TextView balanceView) {
        this.balanceView = balanceView;
    }

    public void setPriceView(TextView priceView) {
        this.priceView = priceView;
    }

    public TextView getBalanceView() {
        return balanceView;
    }

    public TextView getPriceView() {
        return priceView;
    }
}
