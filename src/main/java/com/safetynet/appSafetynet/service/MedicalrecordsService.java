package com.safetynet.appSafetynet.service;

import com.safetynet.appSafetynet.model.ListOfMedicalrecordsModels;
import com.safetynet.appSafetynet.model.MedicalrecordsModel;
import com.safetynet.appSafetynet.repository.IMedicalrecordsRepository;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class MedicalrecordsService {
    @Autowired
    private IMedicalrecordsRepository medicalrecordsRepository;
    static final Logger logger = LogManager.getLogger();

    public ListOfMedicalrecordsModels getMedicalrecords(){
        return medicalrecordsRepository.findAll();
    }
    public MedicalrecordsModel updateMedicalrecords(MedicalrecordsModel model){
        return(medicalrecordsRepository.updateMedicalrecords(model));

    }

    public ListOfMedicalrecordsModels addMedicalrecordsModel(MedicalrecordsModel medicalrecordsModel){
        return(medicalrecordsRepository.addOneMedicalRecords(medicalrecordsModel));

    }
    public ListOfMedicalrecordsModels deleteMedicalRecordsModel(String firstLastName){
        return(medicalrecordsRepository.deleteOneMedicalRecord(firstLastName));

    }
}
