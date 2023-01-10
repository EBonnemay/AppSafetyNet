package com.safetynet.appSafetynet.repository;

import com.jsoniter.any.Any;
import com.safetynet.appSafetynet.model.ListOfPersonModels;
import com.safetynet.appSafetynet.model.ListOfPersonModelsForUrls;
import com.safetynet.appSafetynet.model.PersonModel;
import com.safetynet.appSafetynet.model.PersonModelForUrls;

import java.util.ArrayList;

public interface IPersonRepository {
    public ListOfPersonModelsForUrls fillInPersonModelsForUrls(Any deserializedFile);
    public ListOfPersonModels fillInPersonModels(Any deserializedFile);
    public void setUpListOfPersonModelsForUrls();
    public void setUpListOfPersonModels();
    public ListOfPersonModels findAll();
    public void addOnePerson(PersonModel person);
    public void deleteOnePerson(String firstLastName);
    public void updatePerson(PersonModel updatedPerson );
    public int howOldIsThisPerson(String stringDateOfBirth);
    public ArrayList<PersonModelForUrls> getPeopleInSameHouseHold(String address, ListOfPersonModelsForUrls listOfPersonModelsForUrls);
}
