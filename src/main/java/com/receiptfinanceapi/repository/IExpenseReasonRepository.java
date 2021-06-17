package com.receiptfinanceapi.repository;

import com.receiptfinanceapi.entities.ExpenseReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IExpenseReasonRepository extends JpaRepository<ExpenseReason, Long> {
}
