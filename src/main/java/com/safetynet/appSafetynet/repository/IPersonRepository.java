package com.safetynet.appSafetynet.repository;

import com.jsoniter.any.Any;
import com.safetynet.appSafetynet.model.ListOfPersonModels;
import com.safetynet.appSafetynet.model.PersonModel;

import java.util.ArrayList;

public interface IPersonRepository {
    ListOfPersonModels fillInPersonModels(Any deserializedFile);

    void setUpListOfPersonModels();
    ListOfPersonModels findAll();
    void addOnePerson(PersonModel person);
    void deleteOnePerson(String firstLastName);
    void updatePerson(PersonModel updatedPerson );
    int howOldIsThisPerson(String stringDateOfBirth);
    ArrayList<PersonModel> getPeopleInSameHouseHold(String address, ListOfPersonModels listOfPersonModels);
}
