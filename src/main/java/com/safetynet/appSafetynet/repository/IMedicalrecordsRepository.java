package com.safetynet.appSafetynet.repository;

import com.jsoniter.any.Any;
import com.safetynet.appSafetynet.model.ListOfMedicalrecordsModels;
import com.safetynet.appSafetynet.model.MedicalrecordsModel;

public interface IMedicalrecordsRepository {
    ListOfMedicalrecordsModels fillInMedicalrecordsModels(Any deserializedFile);
    void setUpListOfMedicalrecordsModel();
    ListOfMedicalrecordsModels findAll();
    ListOfMedicalrecordsModels addOneMedicalRecords(MedicalrecordsModel element);
    ListOfMedicalrecordsModels deleteOneMedicalRecord(String firstLastName);
    MedicalrecordsModel updateMedicalrecords(MedicalrecordsModel model);
}
