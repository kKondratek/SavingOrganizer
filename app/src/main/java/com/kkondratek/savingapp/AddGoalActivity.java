package com.kkondratek.savingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddGoalActivity extends AppCompatActivity {

    public static final String EXTRA_NAME =
            "com.kkondratek.savingapp.EXTRA_NAME";
    public static final String EXTRA_DETAILS =
            "com.kkondratek.savingapp.EXTRA_DETAILS";
    public static final String EXTRA_PRICE =
            "com.kkondratek.savingapp.EXTRA_PRICE";

    private EditText editTextName;
    private EditText editTextDetails;
    private EditText editTextPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);

        editTextName = findViewById(R.id.edit_text_name);
        editTextDetails = findViewById(R.id.edit_text_details);
        editTextPrice = findViewById(R.id.edit_text_price);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Goal");
    }

    private void saveGoal() {
        String name = editTextName.getText().toString();
        String details = editTextDetails.getText().toString();
        String price = editTextPrice.getText().toString();

        if (name.trim().isEmpty() || price.trim().isEmpty()) {
            Toast.makeText(this, "Please insert name and price", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_DETAILS, details);
        data.putExtra(EXTRA_PRICE, price);

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
                saveGoal();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
