package com.safetynet.appSafetynet.repository;

import com.jsoniter.any.Any;
import com.safetynet.appSafetynet.model.FirestationModel;
import com.safetynet.appSafetynet.model.ListOfFirestationModels;

import java.util.ArrayList;

public interface IFirestationRepository {
    public ListOfFirestationModels fillInFirestationModels(Any deserializedFile);
    public void setUpListOfFirestationsModel();
    public ListOfFirestationModels findAll();
    public ArrayList<String> findAddressesServedByOneStation(String numberOfStation, ListOfFirestationModels listOfFirestationModels);
    public void deleteOneAddressStationMappingWithAddressParam(String address);
    public void deleteOneOrMoreAddressStationMappingWithStationNumberParam(String stationNumber);
    public void addOneAddressStationMapping(FirestationModel model);
    public void updateFirestationNumberForAnAddress(FirestationModel updatedFirestationModel);
}
