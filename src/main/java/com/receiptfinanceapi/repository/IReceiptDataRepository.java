package com.receiptfinanceapi.repository;

import com.receiptfinanceapi.entities.ReceiptData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReceiptDataRepository extends JpaRepository<ReceiptData, Long> {

}
