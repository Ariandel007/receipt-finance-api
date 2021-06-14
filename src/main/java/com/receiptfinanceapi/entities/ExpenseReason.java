package com.receiptfinanceapi.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "expense_reason")
@Data
public class ExpenseReason {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;
}
