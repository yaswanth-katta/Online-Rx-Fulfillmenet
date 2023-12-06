package com.cts.ordermodule.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class PrescriptionDetails {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long prescriptionId;
    private String rxId;  // type Rx model ,.., drug id or medicine id
//    private long memberId;  //optional
//    private long insurancePolicyNumber;
    private String insuranceProvider;  // name of the company
    private String prescriptionStartDate;   // prescription start date
    private String dosageDefinitionForaDay;
//    private String prescriptionCourse;
    private String doctorDetails;  // or doctor specialization
    private int numberOfRefills; // limit of refilling order
}
