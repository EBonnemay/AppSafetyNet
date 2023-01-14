package com.safetynet.appSafetynet.repository;

import com.jsoniter.any.Any;
import com.safetynet.appSafetynet.model.FirestationModel;
import com.safetynet.appSafetynet.model.ListOfFirestationModels;

import java.util.ArrayList;

public interface IFirestationRepository {
    ListOfFirestationModels fillInFirestationModels(Any deserializedFile);
    void setUpListOfFirestationsModel();
    ListOfFirestationModels findAll();
    ArrayList<String> findAddressesServedByOneStation(String numberOfStation, ListOfFirestationModels listOfFirestationModels);
    void deleteFirestation(FirestationModel model);
    void addOneAddressStationMapping(FirestationModel model);
    void updateFirestationNumberForAnAddress(FirestationModel updatedFirestationModel);
}
