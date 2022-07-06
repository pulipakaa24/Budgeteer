package com.pulipakaa24.budgeteer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.pulipakaa24.budgeteer.BudgetActivity.category;
import static com.pulipakaa24.budgeteer.MainActivity.database;
import static com.pulipakaa24.budgeteer.TransactionActivity.decimalFormat;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.TransactionViewHolder> {

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout containerView;
        public TextView transacNameView;
        public TextView transacAmountView;

        public TransactionViewHolder(@NonNull View view) {
            super(view);
            this.containerView = view.findViewById(R.id.transaction_row);
            this.transacAmountView = view.findViewById(R.id.transacAmountView);
            this.transacNameView = view.findViewById(R.id.transacNameView);

            this.containerView.setOnClickListener(v -> {
                Context context = v.getContext();
                Transaction transaction = (Transaction) containerView.getTag();
                Intent intent = new Intent(v.getContext(), TransactionActivity.class);
                intent.putExtra("id", transaction.id);
                intent.putExtra("name", transaction.transacName);
                intent.putExtra("amount", transaction.amount);

                context.startActivity(intent);
            });
        }
    }

    public static List<Transaction> transactions = new ArrayList<>();

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_row, parent, false);

        return new TransactionViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BudgetAdapter.TransactionViewHolder holder, int position) {
        Transaction current = transactions.get(position);
        holder.containerView.setTag(current);
        holder.transacNameView.setText(current.transacName);
        holder.transacAmountView.setText("$" + decimalFormat.format(current.amount));
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public void reload() {
        notifyDataSetChanged();
        transactions = database.transactionDao().getAll(category);
        notifyDataSetChanged();
    }

}
