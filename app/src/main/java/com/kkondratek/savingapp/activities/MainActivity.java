package com.kkondratek.savingapp.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
    private TextView currencyBalanceView;
    private TextView currencyPriceView;
    private BalanceController balanceController;
    private SharedPreferences sharedPreferences;
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

        sharedPreferences = this.getSharedPreferences(preferencesName,
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

        sharedPreferences.registerOnSharedPreferenceChangeListener(
                new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals("currency")) {
                    String currency = " " + sharedPreferences.getString("currency", "");
                    currencyBalanceView = findViewById(R.id.text_view_balance_currency);
                    currencyPriceView = findViewById(R.id.text_view_price_currency);
                    currencyBalanceView.setText(currency);
                    currencyPriceView.setText(currency);
                }
            }
        });


        //currency setup
        String currency = sharedPreferences.getString("currency", "");

        if (currency.equals("")) {
            openCurrencySettingsDialog();
        } else {
            updateCurrencyViews();
        }


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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.currency_settings:
                openCurrencySettingsDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    private void updateCurrencyViews() {
        String currency = " " + sharedPreferences.getString("currency", "");
        currencyBalanceView = findViewById(R.id.text_view_balance_currency);
        currencyPriceView = findViewById(R.id.text_view_price_currency);
        currencyBalanceView.setText(currency);
        currencyPriceView.setText(currency);
    }

    private void openCurrencySettingsDialog() {
        CurrencySettingDialog currencySettingDialog = new CurrencySettingDialog();
        currencySettingDialog.show(getSupportFragmentManager(), "Currency dialog");
    }

    public static void putStringPref(String key, String value, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(preferencesName, MODE_PRIVATE);
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
        SharedPreferences preferences = context.getSharedPreferences(preferencesName, MODE_PRIVATE);
        return preferences.getString(key, null);
    }

    public static int getIntPref(String key, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(preferencesName, MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }

}
