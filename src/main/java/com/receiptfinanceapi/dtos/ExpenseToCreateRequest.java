package com.receiptfinanceapi.dtos;

import com.receiptfinanceapi.entities.ExpenseReason;
import lombok.Data;

@Data
public class ExpenseToCreateRequest {

    private Boolean isEffective;

    private Boolean isFinal;

    private Double value;

    private ExpenseReason expenseReason;

}
