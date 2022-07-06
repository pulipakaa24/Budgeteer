package com.pulipakaa24.budgeteer;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TransactionDao {
    @Query("Insert into transactions (category, transacName, amount) values (:category, '', 0)")
    long create(String category);

    @Query("select * from transactions where category = :category")
    List<Transaction> getAll(String category);

    @Query("update transactions set transacName = :transacName, amount = :amount where id = :id")
    void save(String transacName, float amount, int id);

    @Query("delete from transactions where id = :id")
    void delete(int id);

    @Query("select * from transactions")
    List<Transaction> getEverything();

    @Query("delete from transactions")
    void deleteAll();
}
