package com.receiptfinanceapi.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class ReceiptToListInTableResponse {
    private Long id;
    private Date createDate;
    private String name;
    private String description;
}
