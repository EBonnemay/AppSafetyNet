package com.safetynet.appSafetynet.service;

import com.safetynet.appSafetynet.model.dto.HouseholdUrl4;
import com.safetynet.appSafetynet.model.dto.ListOfChildrenAndTheHouseholdWithOneAddressUrl2;
import com.safetynet.appSafetynet.model.dto.ListOfHouseholdsCoveredByAFirestationUrl5;
import com.safetynet.appSafetynet.model.dto.ListOfPersonsCoveredByAFirestationUrl1;

import java.util.ArrayList;
import java.util.HashMap;

public interface IUrlService {
    void setUp();
    ListOfPersonsCoveredByAFirestationUrl1 urlOne(String firestationNumber);

    ListOfChildrenAndTheHouseholdWithOneAddressUrl2 urlTwo(String address);
    HashMap<String, ArrayList<String>> urlThree(String numberOfStation);
    HouseholdUrl4 urlFour(String address);
    ListOfHouseholdsCoveredByAFirestationUrl5 urlFive(String stringNumbersOfFirestations);
}
