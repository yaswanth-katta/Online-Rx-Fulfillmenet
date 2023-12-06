package com.cts.refillmodule.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@AllArgsConstructor @NoArgsConstructor @Setter @Getter
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

}
