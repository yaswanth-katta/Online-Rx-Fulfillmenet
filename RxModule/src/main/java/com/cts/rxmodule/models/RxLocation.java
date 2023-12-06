package com.cts.rxmodule.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @AllArgsConstructor @NoArgsConstructor @Setter @Getter
public class RxLocation {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;  // sequence id

    private String rxLocationId;  // location id is uniques for each location
    private String locationName;  // ex:hyd
    private long unitsInPackage;  // ex: 4 units
    private long costPerPackage;  // ex: 1000
    private long quantitiesAvailable;  // Number of packages available
}
