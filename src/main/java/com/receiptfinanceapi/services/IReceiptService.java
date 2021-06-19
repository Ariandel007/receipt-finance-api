package com.receiptfinanceapi.services;


import com.receiptfinanceapi.dtos.ReceiptToCreateRequest;
import com.receiptfinanceapi.dtos.ReceiptToGetResponse;
import com.receiptfinanceapi.dtos.ReceiptToListInTableResponse;
import com.receiptfinanceapi.entities.ReceiptData;
import org.springframework.data.domain.Page;

public interface IReceiptService {
    ReceiptData createReceipt(ReceiptToCreateRequest receiptToCreateRequest, Long idUser) throws Exception;
    Page<ReceiptToListInTableResponse> findAllReceiptsByUserId(int pageNumber, int size, Long idUser) throws Exception;
    ReceiptToGetResponse findOneOfMyReceiptsById(Long idReceipt, Long idUser) throws Exception ;
}
