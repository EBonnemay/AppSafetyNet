package com.safetynet.appSafetynet.model;
//"firstName":"John", "lastName":"Boyd", "birthdate":"03/06/1984",
// "medications":["aznol:350mg", "hydrapermazol:100mg"], "allergies":["nillacilan"] },

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;


@Data
@Component
//@Entity
//@Table(name = "medicalrecords")
public class MedicalrecordsModel {

    private String firstName;
    private String lastName;
    private String birthdate;
    private HashMap<String, String> medications;
    private ArrayList<String> allergies;


}
