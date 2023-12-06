package com.cts.rxmodule.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class ReviewOnDrugs {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int reviewId;
    private String email;
    private String rxName;
    private String comments;
    private int rating;  //rating out o 5

}
