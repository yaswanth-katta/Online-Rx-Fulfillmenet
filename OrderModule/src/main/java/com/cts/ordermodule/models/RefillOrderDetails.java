package com.cts.ordermodule.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor @Getter @Setter @NoArgsConstructor
@Entity
public class RefillOrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long refillOrderId;  // refill id is same as order id
    private long orderId; // if possible type should be Order entity
    private String refillDate; //  date of refilling
    private String refillStatus; // pending, completed
    private String nextRefillDate; // next refill Date
    private String refillOccurrence;  // weekly or monthly
    private int numberOfRefills;
//    private boolean paymentStatus;

    public RefillOrderDetails(long orderId, String refillDate, String refillStatus, String nextRefillDate,String refillOccurrence,int numberOfRefills) {
        this.orderId = orderId;
        this.refillDate = refillDate;
        this.refillStatus = refillStatus;
        this.nextRefillDate = nextRefillDate;
        this.refillOccurrence=refillOccurrence;
        this.numberOfRefills=numberOfRefills;
    }
}
