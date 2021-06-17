package com.receiptfinanceapi.repository;

import com.receiptfinanceapi.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IExpenseRepository extends JpaRepository<Expense, Long> {
}
