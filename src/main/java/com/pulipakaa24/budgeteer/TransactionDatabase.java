package com.pulipakaa24.budgeteer;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Transaction.class}, version = 1)
public abstract class TransactionDatabase extends RoomDatabase {
    public abstract TransactionDao transactionDao();
}
