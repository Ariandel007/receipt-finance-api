package com.receiptfinanceapi.controllers;

import com.receiptfinanceapi.dtos.ReceiptToCreateRequest;

import com.receiptfinanceapi.dtos.ReceiptToGetResponse;
import com.receiptfinanceapi.dtos.ReceiptToListInTableResponse;
import com.receiptfinanceapi.entities.ReceiptData;
import com.receiptfinanceapi.services.IReceiptService;
import com.receiptfinanceapi.utils.TokenUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("receipt")
public class ReceiptController {

    private final IReceiptService receiptService;

    @Autowired
    public ReceiptController(IReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    // Por si añadimos roles a futuro
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/create")
    public ResponseEntity<?> createReceipt(@RequestBody ReceiptToCreateRequest receiptToCreateRequest,
                                           HttpServletRequest request) {
        try {
            Long idUser = TokenUtility.getIdUserFromToken(request);
            ReceiptData receiptToCreateResponse = this.receiptService.createReceipt(receiptToCreateRequest, idUser);
            return ResponseEntity.status(HttpStatus.OK).body(receiptToCreateResponse);
        } catch (Exception e) {
            //e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping(value = "/findAllMyReceipts/{pageNumber}/{size}")
    public ResponseEntity<?> findAllMyReceipts(@PathVariable int pageNumber, @PathVariable int size, HttpServletRequest request) {
        try {
            Long idUser = TokenUtility.getIdUserFromToken(request);
            Page<ReceiptToListInTableResponse> receiptToListInTableResponses = this.receiptService.findAllReceiptsByUserId(pageNumber, size, idUser);
            return ResponseEntity.status(HttpStatus.OK).body(receiptToListInTableResponses);
        } catch (Exception e) {
            //e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping(value = "/findOneOfMyReceiptsById/{idReceipt}")
    public ResponseEntity<?> findOneOfMyReceiptsById(@PathVariable Long idReceipt, HttpServletRequest request) {
        try {
            Long idUser = TokenUtility.getIdUserFromToken(request);
            ReceiptToGetResponse receiptTocalculate = this.receiptService.findOneOfMyReceiptsById(idReceipt, idUser);
            return ResponseEntity.status(HttpStatus.OK).body(receiptTocalculate);
        } catch (Exception e) {
            //e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}
