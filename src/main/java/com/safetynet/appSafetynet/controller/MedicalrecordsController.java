package com.safetynet.appSafetynet.controller;

import com.safetynet.appSafetynet.model.MedicalrecordsModel;
import com.safetynet.appSafetynet.service.MedicalrecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MedicalrecordsController {
    @Autowired
    private MedicalrecordsService medicalrecordsService;

    @GetMapping("/medicalrecords")
    public Iterable<MedicalrecordsModel> getMedicalRecords(){
        return medicalrecordsService.getMedicalrecords();
    }
    @PutMapping("/medicalrecords")
    public void updateAllergiesOrMeds(@RequestParam String firstLastName, String field, String action, String newAllergyOrMed){
        medicalrecordsService.updateAllergiesOrMeds(firstLastName, field, action, newAllergyOrMed);
    }


}
