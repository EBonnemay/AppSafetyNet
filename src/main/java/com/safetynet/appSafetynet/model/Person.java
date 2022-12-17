package com.safetynet.appSafetynet.model;
//persons": [
//        { "firstName":"John", "lastName":"Boyd", "address":"1509 Culver St",
//        "city":"Culver", "zip":"97451", "phone":"841-874-6512", "email":"jaboyd@email.com" },

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Column (name = "first_name")
    private String firstName;

    @Column (name = "last_name")
    private String lastName;

    private String address;
    private String city;

    private String zip;

    private String phone;
    private String email;


}
