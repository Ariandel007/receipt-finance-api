package com.receiptfinanceapi.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class RateToCreateRequest {
    public boolean isCommercialYear;
    public boolean isNominal;
    public Double percentage;
    public Date discountDate;
}
