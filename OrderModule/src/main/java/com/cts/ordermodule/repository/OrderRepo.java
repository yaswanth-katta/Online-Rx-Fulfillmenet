package com.cts.ordermodule.repository;

import com.cts.ordermodule.models.PrescriptionOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<PrescriptionOrder,Long> {
//    @Query("FROM PrescriptionOrder p WHERE p.orderId LIKE :keyword% or p.location LIKE %:keyword%")
    @Query("FROM PrescriptionOrder p WHERE p.location LIKE %:keyword%")
    List<PrescriptionOrder> searchOrderByIdOrName(String keyword);
}
