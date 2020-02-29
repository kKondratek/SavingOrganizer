package com.kkondratek.savingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.kkondratek.savingapp.R;

public class AddSavingActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE =
            "com.kkondratek.savingapp.EXTRA_TITLE";
    public static final String EXTRA_MONTH_DAY =
            "com.kkondratek.savingapp.EXTRA_MONTH_DAY";
    public static final String EXTRA_AMOUNT =
            "com.kkondratek.savingapp.EXTRA_AMOUNT";

    private EditText editTextTitle;
    private EditText editTextAmount;
    private NumberPicker numberPickerMonthDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_saving);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextAmount = findViewById(R.id.edit_text_amount);
        numberPickerMonthDay = findViewById(R.id.number_picker_month_day);

        numberPickerMonthDay.setMinValue(1);
        numberPickerMonthDay.setMaxValue(31);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Saving");
    }

    private void saveSaving() {
        String title = editTextTitle.getText().toString();
        String amount = editTextAmount.getText().toString();
        String monthDay = String.valueOf(numberPickerMonthDay.getValue());

        if (title.trim().isEmpty() || amount.trim().isEmpty()) {
            Toast.makeText(this, "Please insert title and amount", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_AMOUNT, amount);
        data.putExtra(EXTRA_MONTH_DAY, monthDay);

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_item:
                saveSaving();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}