package com.receiptfinanceapi.repository;

import com.receiptfinanceapi.entities.CompoundingPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICompoundingPeriodRepository extends JpaRepository<CompoundingPeriod, Long> {
}
