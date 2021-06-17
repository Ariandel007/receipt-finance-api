package com.receiptfinanceapi.repository;

import com.receiptfinanceapi.entities.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRateRepository extends JpaRepository<Rate, Long> {
}
