package com.kkondratek.savingapp.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.kkondratek.savingapp.R;
import com.kkondratek.savingapp.fragments.GoalsPageFragm;
import com.kkondratek.savingapp.fragments.SavingsPageFragm;
import com.kkondratek.savingapp.logic.BalanceController;
import com.kkondratek.savingapp.logic.MainTextViews;
import com.kkondratek.savingapp.logic.SlidePagerAdapter;
import com.kkondratek.savingapp.logic.UpdateTextEvent;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {

    private EventBus bus = EventBus.getDefault();
    private TextView totalPriceView;
    private TextView balanceView;
    private BalanceController balanceController;
    public static final String preferencesName = "app_prefs";


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

        SharedPreferences sharedPreferences = this.getSharedPreferences(preferencesName,
                Context.MODE_PRIVATE);

        balanceView = findViewById(R.id.text_view_balance);
        totalPriceView = findViewById(R.id.text_view_total_price);
        bus.register(this);

        balanceController = new BalanceController(balanceView, sharedPreferences);

        ImageButton addAmount = findViewById(R.id.addAmount);
        ImageButton substractAmount = findViewById(R.id.subAmount);

        final EditText amountInput = findViewById(R.id.edit_text_amount);

        MainTextViews mainTextViews = MainTextViews.instance();
        mainTextViews.setBalanceView(balanceView);
        mainTextViews.setPriceView(totalPriceView);

        addAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                balanceController.addAmount(amountInput);
                closeKeyboard();
            }
        });

        substractAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                balanceController.substractAmount(amountInput);
                closeKeyboard();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public void onEvent(UpdateTextEvent event) {
        totalPriceView.setText(event.getTextValue());
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public static void putStringPref(String key, String value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void putIntPref(String key, int value, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(preferencesName, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static String getStringPref(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    public static int getIntPref(String key, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(preferencesName, MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }

}
