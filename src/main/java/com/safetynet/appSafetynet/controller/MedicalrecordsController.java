package com.safetynet.appSafetynet.controller;

import com.safetynet.appSafetynet.model.ListOfMedicalrecordsModels;
import com.safetynet.appSafetynet.model.MedicalrecordsModel;
import com.safetynet.appSafetynet.service.MedicalrecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MedicalrecordsController {
    @Autowired
    private MedicalrecordsService medicalrecordsService;

    @GetMapping("/medicalRecord")
    public ListOfMedicalrecordsModels getMedicalRecords(){
        return medicalrecordsService.getMedicalrecords();
    }

    @PutMapping("/medicalRecord")
    public void updateAllergiesOrMeds(@RequestBody MedicalrecordsModel model){
        medicalrecordsService.updateAllergiesOrMeds(model);
    }
    @PostMapping("/medicalRecord")
    public void addMedicalrecord(@RequestBody MedicalrecordsModel medicalrecordModel){
        medicalrecordsService.addMedicalrecordsModel(medicalrecordModel);
    }
    @DeleteMapping("/medicalRecord")
    public void deleteMedicalrecord(@RequestParam String firstLastName){
        medicalrecordsService.deleteMedicalRecordsModel(firstLastName);
    }

}
