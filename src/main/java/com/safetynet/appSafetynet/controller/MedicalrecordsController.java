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
    public MedicalrecordsModel updateMedicalrecords(@RequestBody MedicalrecordsModel model){
        MedicalrecordsModel result= new MedicalrecordsModel();
        try{
            result = medicalrecordsService.updateMedicalrecords(model);
            logger.info("medical record updated successfully");
            return result;
        }catch(RuntimeException e){
            logger.error("medical record updating failed");
            return result;
        }
    }

    @PostMapping("/medicalRecord")
    public ListOfMedicalrecordsModels addMedicalrecord(@RequestBody MedicalrecordsModel model){
        ListOfMedicalrecordsModels result = new ListOfMedicalrecordsModels();
        try {
            result = medicalrecordsService.addMedicalrecordsModel(model);
            logger.info("medical record added successfully");
            return result;
        } catch (RuntimeException e) {
            logger.error("medical record adding failed");
            return result;
        }

    }
    @DeleteMapping("/medicalRecord")
    public ListOfMedicalrecordsModels deleteMedicalrecord(@RequestParam String firstLastName){
        ListOfMedicalrecordsModels result = new ListOfMedicalrecordsModels();
        try {
            result = medicalrecordsService.deleteMedicalRecordsModel(firstLastName);
            logger.info("medical record deleted successfully");
            return result;
        } catch (RuntimeException e) {
            logger.error("medical record deleting failed");
            return result;
        }
    }

}
