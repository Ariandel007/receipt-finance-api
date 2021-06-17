package com.receiptfinanceapi.dtos;

import lombok.Data;

@Data
public class CompoundingPeriodToCreateRequest {
    private Long id;

    private String name;

    private Integer numberDays;

}
