package com.pulipakaa24.budgeteer;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.pulipakaa24.budgeteer.MainActivity.database;

public class ResetConfirmActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetconfirm);
        TextView sure = findViewById(R.id.sure);
        sure.setTextColor(Color.RED);
    }

    public void yes(View view) {
        database.transactionDao().deleteAll();
        finish();
    }

    public void no(View view) {
        finish();
    }
}
