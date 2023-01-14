package com.safetynet.appSafetynet.repository;

import com.jsoniter.any.Any;
import com.safetynet.appSafetynet.model.ListOfMedicalrecordsModels;
import com.safetynet.appSafetynet.model.MedicalrecordsModel;

public interface IMedicalrecordsRepository {
    ListOfMedicalrecordsModels fillInMedicalrecordsModels(Any deserializedFile);
    void setUpListOfMedicalrecordsModel();
    ListOfMedicalrecordsModels findAll();
    void addOneMedicalRecords(MedicalrecordsModel element);
    void deleteOneMedicalRecord(String firstLastName);
    void updateMedicalrecords(MedicalrecordsModel model);
}
