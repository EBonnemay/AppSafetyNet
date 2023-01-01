package com.safetynet.appSafetynet.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class HouseholdUrl4 {
    private String FirestationNumber;
    private String address;
    private List <PersonInfoForUrl4And5> listOfPersons = new ArrayList<>();


}
