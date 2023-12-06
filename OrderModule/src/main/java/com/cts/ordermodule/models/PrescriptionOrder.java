package com.cts.ordermodule.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class PrescriptionOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;
    private long memberId;
    private String orderDate;
    private String nextRefillDate; // refill Date
    private String refillOccurrence; // weekly or monthly
    private String location;
    private String orderStatus; // pending, completed or cancel
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private PrescriptionDetails prescriptionDetails; // Element Collections, one to one mapping
}
