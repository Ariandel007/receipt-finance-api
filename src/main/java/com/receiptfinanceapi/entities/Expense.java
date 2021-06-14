package com.receiptfinanceapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "expenses")
@Data
public class Expense {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_effective")
    private Boolean isEffective;

    @Column(name = "is_final")
    private Boolean isFinal;

    @Column(precision = 10, scale = 2)
    private Double value;

    @Column(name = "id_expense_reason", nullable = false)
    private Long idExpenseReason;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_expense_reason", referencedColumnName = "id", insertable = false, updatable = false, nullable = false)
    private ExpenseReason expenseReason;

    @Column(name = "id_rate", nullable = false)
    private Long idRate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rate", referencedColumnName = "id", insertable = false, updatable = false)
    private Rate rate;

}
