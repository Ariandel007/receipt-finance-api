package com.receiptfinanceapi.repository;

import com.receiptfinanceapi.entities.RateTerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRateTermRepository extends JpaRepository<RateTerm, Long> {
}
