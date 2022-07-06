package com.pulipakaa24.budgeteer;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transactions")
public class    Transaction {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name = "transacName")
    public String transacName;

    @ColumnInfo(name = "amount")
    public float amount;
}
