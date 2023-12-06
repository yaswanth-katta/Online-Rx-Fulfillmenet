package com.cts.refillmodule.repository;

import com.cts.refillmodule.models.RefillOrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefillOrderRepo extends JpaRepository<RefillOrderDetails,Long> {
    RefillOrderDetails findByOrderId(long orderId);

    @Query("FROM RefillOrderDetails ro WHERE ro.nextRefillDate != 'none' AND CAST(ro.nextRefillDate AS DATE) < CURRENT_DATE")
    List<RefillOrderDetails> getDueRefillOrders();
}
