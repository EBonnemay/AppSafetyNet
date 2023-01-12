package com.safetynet.appSafetynet.repository;

import com.jsoniter.any.Any;
import com.safetynet.appSafetynet.model.ListOfPersonModels;
import com.safetynet.appSafetynet.model.ListOfPersonModelsForUrls;
import com.safetynet.appSafetynet.model.PersonModel;
import com.safetynet.appSafetynet.model.PersonModelForUrls;

import java.util.ArrayList;

public interface IPersonRepository {
    ListOfPersonModelsForUrls fillInPersonModelsForUrls(Any deserializedFile);
    ListOfPersonModels fillInPersonModels(Any deserializedFile);
    void setUpListOfPersonModelsForUrls();
    void setUpListOfPersonModels();
    ListOfPersonModels findAll();
    void addOnePerson(PersonModel person);
    void deleteOnePerson(String firstLastName);
    void updatePerson(PersonModel updatedPerson );
    int howOldIsThisPerson(String stringDateOfBirth);
    ArrayList<PersonModelForUrls> getPeopleInSameHouseHold(String address, ListOfPersonModelsForUrls listOfPersonModelsForUrls);
}
