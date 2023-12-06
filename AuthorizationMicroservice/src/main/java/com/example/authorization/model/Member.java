package com.example.authorization.model;

import lombok.Data;

import javax.persistence.*;

@Entity @Data
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(unique = true)
    private String email;  // use as username

    private String fullName;
    private String gender;
    private int age;
    private long contactNumber;
    private String password;
    private String address;
}

