package com.safetynet.appSafetynet.controller;

import com.safetynet.appSafetynet.model.ListOfMedicalrecordsModels;
import com.safetynet.appSafetynet.model.MedicalrecordsModel;
import com.safetynet.appSafetynet.service.MedicalrecordsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MedicalrecordsController {
    @Autowired
    private MedicalrecordsService medicalrecordsService;
    static final Logger logger = LogManager.getLogger();
    @GetMapping("/medicalRecord")
    public ListOfMedicalrecordsModels getMedicalRecords(){
        return medicalrecordsService.getMedicalrecords();
    }

    @PutMapping("/medicalRecord")
    public void updateAllergiesOrMeds(@RequestBody MedicalrecordsModel model){
        medicalrecordsService.updateAllergiesOrMeds(model);
        logger.info("Medicalrecords updated successfully");
    }
    @PostMapping("/medicalRecord")
    public void addMedicalrecord(@RequestBody MedicalrecordsModel medicalrecordModel){
        medicalrecordsService.addMedicalrecordsModel(medicalrecordModel);
        logger.info("Medicalrecords added successfully");
    }
    @DeleteMapping("/medicalRecord")
    public void deleteMedicalrecord(@RequestParam String firstLastName){
        medicalrecordsService.deleteMedicalRecordsModel(firstLastName);
        logger.info("Medicalrecords deleted successfully");
    }

}
