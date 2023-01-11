package com.safetynet.appSafetynet.repository;

import com.jsoniter.any.Any;
import com.safetynet.appSafetynet.model.ListOfMedicalrecordsModels;
import com.safetynet.appSafetynet.model.MedicalrecordsModel;

public interface IMedicalrecordsRepository {
    public ListOfMedicalrecordsModels fillInMedicalrecordsModels(Any deserializedFile);
    public void setUpListOfMedicalrecordsModel();
    public ListOfMedicalrecordsModels findAll();
    public void addOneMedicalRecords(MedicalrecordsModel element);
    public void deleteOneMedicalRecord(String firstLastName);
    public void updateAllergiesOrMeds(MedicalrecordsModel model);
}
