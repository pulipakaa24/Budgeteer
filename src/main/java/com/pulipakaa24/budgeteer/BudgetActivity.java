package com.pulipakaa24.budgeteer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.pulipakaa24.budgeteer.MainActivity.database;
import static com.pulipakaa24.budgeteer.MainActivity.shared_prefs;
import static com.pulipakaa24.budgeteer.TransactionActivity.decimalFormat;

public class BudgetActivity extends AppCompatActivity {
    public static BudgetAdapter adapter;
    public static String category;
    Context context;
    private List<Transaction> transactions;
    private TextView categoryTotal;
    private TextView budgetAmount;
    private float total;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        TextView categoryName = findViewById(R.id.category_name);
        categoryTotal = findViewById(R.id.category_amount);
        RecyclerView recyclerView = findViewById(R.id.category_recyclerView);
        budgetAmount = findViewById(R.id.budget_amount);

        context = this;

        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        categoryName.setText(category);

        transactions = database.transactionDao().getAll(category);
        total = 0;

        for (int i = 0; i < transactions.size(); i++) {
            total += transactions.get(i).amount;
        }

        categoryTotal.setText("$" + decimalFormat.format(total));
        budgetButton();

        adapter = new BudgetAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        FloatingActionButton addTransac = findViewById(R.id.add_transac_button);
        addTransac.setOnClickListener(v -> {
            int id = (int) database.transactionDao().create(intent.getStringExtra("category"));
            adapter.reload();

            Context context = v.getContext();
            Intent intent1 = new Intent(context, TransactionActivity.class);
            intent1.putExtra("id", id);
            context.startActivity(intent1);
        });
    }

    @SuppressLint("SetTextI18n")
    private void budgetButton () {
        SharedPreferences sharedPreferences = getSharedPreferences(shared_prefs, MODE_PRIVATE);
        float f = sharedPreferences.getFloat(category, 0.00f);
        if (f != 0) {
            budgetAmount.setText("Budget $" + decimalFormat.format(f));
            if (total > f) {
                categoryTotal.setTextColor(Color.RED);
            }

            else {
                categoryTotal.setTextColor(Color.GREEN);
            }
        }

        else {
            budgetAmount.setText("Add Budget");
            categoryTotal.setTextColor(Color.GREEN);
        }

        budgetAmount.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent1 = new Intent(context, BudgetcreateActivity.class);
            intent1.putExtra("category", category);
            intent1.putExtra("amount", f);
            context.startActivity(intent1);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.reload();

        transactions = database.transactionDao().getAll(category);
        total = 0;

        for (int i = 0; i < transactions.size(); i++) {
            total += transactions.get(i).amount;
        }

        final String total1 = "$" + decimalFormat.format(total);

        categoryTotal.setText(total1);

        budgetButton();
    }

    public void back(View view) {
        finish();
    }
}
