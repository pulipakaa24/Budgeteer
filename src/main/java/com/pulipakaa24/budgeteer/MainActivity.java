package com.pulipakaa24.budgeteer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;

import static com.pulipakaa24.budgeteer.TransactionActivity.decimalFormat;

public class MainActivity extends AppCompatActivity {
    public static TransactionDatabase database;
    public static final String shared_prefs = "SHARED_PREFS";
    public static final String total_budget = "TOTAL_BUDGET";
    public static final String fbudget = "Food and Drink";
    public static final String gbudget = "Grocery";
    public static final String ebudget = "Entertainment";
    public static final String tbudget = "Travel";
    public static final String hebudget = "Health";
    public static final String cbudget = "Clothing";
    public static final String ubudget = "Utilities";
    public static final String hobudget = "Home";
    public static final String obudget = "Other";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        database = Room
                .databaseBuilder(getApplicationContext(), TransactionDatabase.class, "transactions")
                .allowMainThreadQueries()
                .build();

        init();
        budgetButton();
    }

    @SuppressLint("SetTextI18n")
    private void budgetButton() {
        SharedPreferences sharedPreferences = getSharedPreferences(shared_prefs, MODE_PRIVATE);
        TextView totalBudget = findViewById(R.id.budgetText);

        if (sharedPreferences.getFloat(total_budget, 0.00f) == 0.0) {
            totalBudget.setText("Add Total Budget");
        }

        else {
            totalBudget.setText("Budget $" + decimalFormat.format(
                    sharedPreferences.getFloat(total_budget, 0.00f)));
        }

        totalBudget.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, BudgetcreateActivity.class);
            intent.putExtra("category", total_budget);
            intent.putExtra("amount", sharedPreferences.getFloat(total_budget, 0.00f));
            context.startActivity(intent);
        });
    }

    private void init () {
        List<Transaction> transactions = database.transactionDao().getEverything();
        TextView totalView = findViewById(R.id.totalAmountText);
        SharedPreferences sharedPreferences = getSharedPreferences(shared_prefs, MODE_PRIVATE);
        float f = sharedPreferences.getFloat(total_budget, 0.00f);
        checkBudget(totalView, setTotalString(transactions, totalView), f);
        TextView food = findViewById(R.id.foodAmountView);
        TextView entert = findViewById(R.id.entertAmountView);
        TextView trav = findViewById(R.id.TravelAmountView);
        TextView health = findViewById(R.id.HealthAmountView);
        TextView cloth = findViewById(R.id.clothingAmountView);
        TextView util = findViewById(R.id.utilAmountView);
        TextView home = findViewById(R.id.homeAmountView);
        TextView other = findViewById(R.id.otherAmountView);
        TextView groc = findViewById(R.id.GroceriesAmountView);

        float foodDrink = sharedPreferences.getFloat(fbudget, 0.00f);
        float entertBud = sharedPreferences.getFloat(ebudget, 0.00f);
        float travelBud = sharedPreferences.getFloat(tbudget, 0.00f);
        float healthBud = sharedPreferences.getFloat(hebudget, 0.00f);
        float clothBud = sharedPreferences.getFloat(cbudget, 0.00f);
        float utilBud = sharedPreferences.getFloat(ubudget, 0.00f);
        float homeBud = sharedPreferences.getFloat(hobudget, 0.00f);
        float otherBud = sharedPreferences.getFloat(obudget, 0.00f);
        float grocBud = sharedPreferences.getFloat(gbudget, 0.00f);

        List<Transaction> foodDrinkTransac = database.transactionDao().getAll("Food and Drink");
        checkBudget(food, setTotalString(foodDrinkTransac, food), foodDrink);
        List<Transaction> entertTransac = database.transactionDao().getAll("Entertainment");
        checkBudget(entert, setTotalString(entertTransac, entert), entertBud);
        List<Transaction> travelTransac = database.transactionDao().getAll("Travel");
        checkBudget(trav, setTotalString(travelTransac, trav), travelBud);
        List<Transaction> healthTransac = database.transactionDao().getAll("Health");
        checkBudget(health, setTotalString(healthTransac, health), healthBud);
        List<Transaction> clothingTransac = database.transactionDao().getAll("Clothing");
        checkBudget(cloth, setTotalString(clothingTransac, cloth), clothBud);
        List<Transaction> utilityTransac = database.transactionDao().getAll("Utilities");
        checkBudget(util, setTotalString(utilityTransac, util), utilBud);
        List<Transaction> homeTransac = database.transactionDao().getAll("Home");
        checkBudget(home, setTotalString(homeTransac, home), homeBud);
        List<Transaction> otherTransac = database.transactionDao().getAll("Other");
        checkBudget(other, setTotalString(otherTransac, other), otherBud);
        List<Transaction> groceryTransac = database.transactionDao().getAll("Grocery");
        checkBudget(groc, setTotalString(groceryTransac, groc), grocBud);
    }

    public static void checkBudget (TextView t, float f, float y) {
        if (f > y & y != 0) {
            t.setTextColor(Color.RED);
        }

        else {
            t.setTextColor(Color.GREEN);
        }
    }

    @SuppressLint("SetTextI18n")
    private float setTotalString (List<Transaction> transactions, TextView textView) {
        float total = 0;
        for (int i = 0; i < transactions.size(); i++) {
            total += transactions.get(i).amount;
        }
        textView.setText("$" + decimalFormat.format(total));
        return total;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        init();
        budgetButton();
    }

    private void open(Context context, String category) {
        Intent intent = new Intent(context, BudgetActivity.class);
        intent.putExtra("category", category);
        context.startActivity(intent);
    }

    public void openFoodDrink(View view) {
        open(view.getContext(), fbudget);
    }

    public void openEntertainment(View view) {
        open(view.getContext(), ebudget);
    }

    public void openGrocery(View view) {
        open(view.getContext(), gbudget);
    }

    public void openTravel(View view) {
        open(view.getContext(), tbudget);
    }

    public void openHealth(View view) {
        open(view.getContext(), hebudget);
    }

    public void openClothing(View view) {
        open(view.getContext(), cbudget);
    }

    public void openUtilities(View view) {
        open(view.getContext(), ubudget);
    }

    public void openHome(View view) {
        open(view.getContext(), hobudget);
    }

    public void openOther(View view) {
        open(view.getContext(), obudget);
    }

    public void openReset(View view) {
        Context context = view.getContext();
        context.startActivity(new Intent(context, ResetConfirmActivity.class));
    }
}