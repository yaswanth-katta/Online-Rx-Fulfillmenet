package com.cts.ordermodule.repository;

import com.cts.ordermodule.models.RefillOrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefillRepo extends JpaRepository<RefillOrderDetails,Long> {
    RefillOrderDetails findByOrderId(long orderId);
    void deleteByOrderId(long orderId);
}
