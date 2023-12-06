package com.cts.rxmodule.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity @AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class Rx {  // details of the medicine or drugs
    @Id
    private String rxId;
    private String rxName;
    private String manufacturedDate;  // type is date or string
    private String expiryDate; // type is date or string

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<RxLocation> rxLocation; // quantity details , one to many mapping

}
