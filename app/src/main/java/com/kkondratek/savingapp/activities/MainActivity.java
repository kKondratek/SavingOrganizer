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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.kkondratek.savingapp.R;
import com.kkondratek.savingapp.data.Goal;
import com.kkondratek.savingapp.fragments.GoalsPageFragm;
import com.kkondratek.savingapp.fragments.SavingsPageFragm;
import com.kkondratek.savingapp.logic.BalanceController;
import com.kkondratek.savingapp.logic.GoalViewModel;
import com.kkondratek.savingapp.logic.MainTextViews;
import com.kkondratek.savingapp.logic.UpdateTextEvent;
import com.kkondratek.savingapp.logic.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {

    private EventBus bus = EventBus.getDefault();
    private TextView totalPriceView;
    private TextView balanceView;
    private TextView currencyBalanceView;
    private TextView currencyPriceView;
    private TextView savingsTabView;
    private TextView goalsTabView;
    private BalanceController balanceController;
    private SharedPreferences sharedPreferences;
    private TabLayout tabLayout;
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

        final ViewPager2 pager = findViewById(R.id.view_pager);
        //new ViewPagerAdapter(getSupportFragmentManager(), pageList);

        tabLayout = findViewById(R.id.tab_layout);
        pager.setAdapter(createCardAdapter());

        new TabLayoutMediator(tabLayout, pager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        if (position == 0) {
                            tab.setText(R.string.savings);
                        } else {
                            tab.setText(R.string.goals);
                        }
                    }
                }).attach();


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

        initializeTotalPrice();

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

    /**
     * Updates all views linked with currency
     */
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

    private ViewPagerAdapter createCardAdapter() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        return adapter;
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

    /**
     * Initializes Goal data and assigns total price value
     */
    private void initializeTotalPrice() {
        final GoalViewModel goalViewModel = ViewModelProviders.of(this).get(GoalViewModel.class);
        goalViewModel.getAllGoals().observe(this, new Observer<List<Goal>>() {
                    @Override
                    public void onChanged(List<Goal> goals) {
                        int totalPrice = 0;
                        System.out.println("MainActivity price");
                        if (goals.size() > 0) {
                            for (Goal goal : goals) {
                                System.out.println(goal.toString());
                                totalPrice += Integer.parseInt(goal.getPrice());
                            }
                        }
                        totalPriceView.setText(String.valueOf(totalPrice));
                        goalViewModel.getAllGoals().removeObserver(this);
                    }
                }
        );

    }

}
