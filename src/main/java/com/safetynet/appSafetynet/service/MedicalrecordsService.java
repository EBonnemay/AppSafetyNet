package com.safetynet.appSafetynet.service;

import com.safetynet.appSafetynet.model.ListOfMedicalrecordsModels;
import com.safetynet.appSafetynet.model.MedicalrecordsModel;
import com.safetynet.appSafetynet.repository.MedicalrecordsRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class MedicalrecordsService {
    @Autowired
    private MedicalrecordsRepository medicalrecordsRepository;

    public ListOfMedicalrecordsModels getMedicalrecords(){
        return medicalrecordsRepository.findAll();
    }
    public void updateAllergiesOrMeds(MedicalrecordsModel model){
        medicalrecordsRepository.updateAllergiesOrMeds(model);
    }

    public void addMedicalrecordsModel(MedicalrecordsModel medicalrecordsModel){
        medicalrecordsRepository.addOneMedicalRecords(medicalrecordsModel);
    }
    public void deleteMedicalRecordsModel(String firstLastName){
        medicalrecordsRepository.deleteOneMedicalRecord(firstLastName);
    }
}
