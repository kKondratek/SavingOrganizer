package com.kkondratek.savingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kkondratek.savingapp.fragments.GoalsPageFragm;
import com.kkondratek.savingapp.fragments.SavingsPageFragm;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {

    private EventBus bus = EventBus.getDefault();
    private TextView totalPriceView;
    private BalanceController balanceController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Fragment> pageList = new ArrayList<>();
        GoalsPageFragm goalsPageFragm = new GoalsPageFragm();
        SavingsPageFragm savingsPageFragm = new SavingsPageFragm();
        pageList.add(savingsPageFragm);
        pageList.add(goalsPageFragm);

        ViewPager pager = findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter = new SlidePagerAdapter(getSupportFragmentManager(), pageList);

        pager.setAdapter(pagerAdapter);

        SharedPreferences balance = this.getSharedPreferences("Balance", Context.MODE_PRIVATE);
        TextView balanceView = findViewById(R.id.text_view_balance);

        totalPriceView = findViewById(R.id.text_view_total_price);
        bus.register(this);

        balanceController = new BalanceController(balanceView, balance);

//        savingViewModel = ViewModelProviders.of(this).get(SavingViewModel.class);
//        savingViewModel.getAllSavings().observe(this, new Observer<List<Saving>>() {
//            @Override
//            public void onChanged(List<Saving> savings) {
//
//            }
//        });

        ImageButton addAmount = findViewById(R.id.addAmount);
        ImageButton substractAmount = findViewById(R.id.subAmount);

        final EditText amountInput = findViewById(R.id.edit_text_amount);

        addAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                balanceController.addAmount(amountInput);
            }
        });

        substractAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                balanceController.substractAmount(amountInput);
            }
        });

    }

    public void onEvent(UpdateTextEvent event) {
        totalPriceView.setText(event.getTextValue());
    }

}
