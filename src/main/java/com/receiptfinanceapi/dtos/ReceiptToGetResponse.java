package com.receiptfinanceapi.dtos;

import com.receiptfinanceapi.entities.CompoundingPeriod;
import com.receiptfinanceapi.entities.Expense;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ReceiptToGetResponse {
    public Boolean isDolar;
    public String description;
    public String name;
    public Double retainage;
    public Double totalValue;
    public Date paymentDate;
    public Date dateOfIssue;
    public RateToCreateRequest rate;
    public RateTermToCreateRequest rateTerm;
    public CompoundingPeriod compoundingPeriod;
    public List<Expense> expenses;
}
