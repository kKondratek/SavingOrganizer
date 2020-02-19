package com.kkondratek.savingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.ColumnInfo;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.FrameMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kkondratek.savingapp.fragments.GoalsPageFragm;
import com.kkondratek.savingapp.fragments.SavingsPageFragm;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private PagerAdapter pagerAdapter;

    private BalanceController balanceController;


//    private SavingViewModel savingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Fragment> pageList = new ArrayList<>();
        GoalsPageFragm goalsPageFragm = new GoalsPageFragm();
        SavingsPageFragm savingsPageFragm = new SavingsPageFragm();
        pageList.add(savingsPageFragm);
        pageList.add(goalsPageFragm);

        pager = findViewById(R.id.viewPager);
        pagerAdapter = new SlidePagerAdapter(getSupportFragmentManager(), pageList);

        pager.setAdapter(pagerAdapter);

        SharedPreferences balance = this.getSharedPreferences("Balance", Context.MODE_PRIVATE);
        TextView balanceView = (TextView) findViewById(R.id.balance);

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

        final TextInputEditText amountInput = (TextInputEditText) findViewById(R.id.amountInput);

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

}
