package com.pulipakaa24.budgeteer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.pulipakaa24.budgeteer.MainActivity.shared_prefs;
import static com.pulipakaa24.budgeteer.TransactionActivity.decimalFormat;

public class BudgetcreateActivity extends AppCompatActivity {
    private EditText budgetEdit;
    private View v;
    private String category;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budgetcreate);
        budgetEdit = findViewById(R.id.budgetEdit);

        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        float f = intent.getFloatExtra("amount", 0.00f);
        if (f != 0) {
            budgetEdit.setText(decimalFormat.format(f));
        }
    }

    public void back(View view) {
        v = view;
        SharedPreferences sharedPreferences = getSharedPreferences(shared_prefs, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!budgetEdit.getText().toString().equals("")) {
            editor.putFloat(category, Float.parseFloat(budgetEdit.getText().toString().replaceAll("\\$", "")));
        }
        else {
            editor.putFloat(category, 0.00f);
        }
        editor.apply();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        back(v);
    }
}
