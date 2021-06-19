package com.receiptfinanceapi.repository;

import com.receiptfinanceapi.entities.ReceiptData;
import com.receiptfinanceapi.projection.IReceiptListProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IReceiptDataRepository extends JpaRepository<ReceiptData, Long> {

    @Query(value = "SELECT id, create_date as createDate, description, \"name\" " +
            "FROM public.receipt_data " +
            "WHERE id_user =:userId ",
            countQuery = "SELECT count(1) FROM public.receipt_data ",
            nativeQuery = true)
    Page<IReceiptListProjection> publicationsFindedForUserId(@Param("userId") Long userId, Pageable pageable);

    ReceiptData findByIdAndIdUser(Long id, Long idUser);
}
