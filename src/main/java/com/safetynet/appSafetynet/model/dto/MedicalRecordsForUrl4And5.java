package com.safetynet.appSafetynet.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Data
public class MedicalRecordsForUrl4And5 {
    private List<String> listOfAllergies = new ArrayList<>();
    private HashMap<String, String > medications = new HashMap<>();
}
