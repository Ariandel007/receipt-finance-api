package com.receiptfinanceapi.dtos;

import lombok.Data;

@Data
public class RateTermToCreateRequest {
    public Long id;
    public String name;
    private Integer numberDays;
}