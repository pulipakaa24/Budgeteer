package com.pulipakaa24.budgeteer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

import static com.pulipakaa24.budgeteer.BudgetActivity.adapter;
import static com.pulipakaa24.budgeteer.MainActivity.database;

public class TransactionActivity extends AppCompatActivity {
    private EditText nameEdit;
    private EditText amountEdit;
    public static DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        nameEdit = findViewById(R.id.transacNameEdit);
        amountEdit = findViewById(R.id.transacAmountEdit);

        Intent intent = getIntent();
        nameEdit.setText(intent.getStringExtra("name"));
        float amount = intent.getFloatExtra("amount", 0.00f);
        if (amount != 0) {
            amountEdit.setText(decimalFormat.format(amount));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);

        String s = amountEdit.getText().toString();
        float f;

        if (!s.equals("")) {
            f = Float.parseFloat(s);
        }

        else {
            f = 0.00f;
        }

        database.transactionDao().save(nameEdit.getText().toString(), f, id);
        adapter.reload();
    }

    public void delete(View view) {
        database.transactionDao().delete(getIntent().getIntExtra("id", 0));
        finish();
    }

    public void back(View view) {
        onPause();
        finish();
    }
}
