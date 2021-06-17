package com.receiptfinanceapi.services;


import com.receiptfinanceapi.dtos.ReceiptToCreateRequest;
import com.receiptfinanceapi.entities.ReceiptData;

public interface IReceiptService {
    ReceiptData createReceipt(ReceiptToCreateRequest receiptToCreateRequest, Long idUser) throws Exception ;

}
