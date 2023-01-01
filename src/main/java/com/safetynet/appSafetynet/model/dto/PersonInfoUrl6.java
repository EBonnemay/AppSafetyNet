package com.safetynet.appSafetynet.model.dto;

import lombok.Data;

@Data
public class PersonInfoUrl6 {
    private String firstName;
    private String lastName;
    private String address;
    private int age;
    private String mailAddress;
    private MedicalRecordsForUrl4And5 medicalRecordsForUrl4And5 = new MedicalRecordsForUrl4And5();
}
