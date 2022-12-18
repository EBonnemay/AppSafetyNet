package com.safetynet.appSafetynet.model;
//"firstName":"John", "lastName":"Boyd", "birthdate":"03/06/1984",
// "medications":["aznol:350mg", "hydrapermazol:100mg"], "allergies":["nillacilan"] },

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;


@Data
@Entity
@Table(name = "medicalrecords")
public class Medicalrecords {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Column(name = "first_name")
    private String firstName;

    @Column (name = "last_name")
    private String lastName;
    private String birthdate;
    private ArrayList<String> medications;
    private ArrayList<String> allergies;


}
