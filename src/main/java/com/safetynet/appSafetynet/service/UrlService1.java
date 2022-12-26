package com.safetynet.appSafetynet.service;

import com.safetynet.appSafetynet.model.FirestationModel;
import com.safetynet.appSafetynet.model.MedicalrecordsModel;
import com.safetynet.appSafetynet.model.PersonModel;
import com.safetynet.appSafetynet.repository.FirestationRepository;
import com.safetynet.appSafetynet.repository.MedicalrecordsRepository;
import com.safetynet.appSafetynet.repository.PersonRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
@Data
@Service
public class UrlService1 {
    @Autowired
    FirestationRepository firestationRepository;
    @Autowired
    MedicalrecordsRepository medicalrecordsRepository;
    @Autowired
    PersonRepository personRepository;



   ArrayList<PersonModel> arrayListPersons;
   ArrayList<FirestationModel> arrayListFirestations;
   ArrayList<MedicalrecordsModel> arrayListMedicalrecords;


    HashMap<String, Integer> mapOfChildrenServedByOneStation = new HashMap<String, Integer>();
    HashMap<String, ArrayList> mapOfPersonsServedByOneStation = new HashMap<>();

    public HashMap<String, ArrayList> mapOfPersonsServedByOneStation(String numberOfStation){



        for (FirestationModel element : arrayListFirestations) {
            if (element.getStation().equals(numberOfStation)) {
                String address = element.getAddress();
                for (PersonModel person : arrayListPersons){
                    if(person.getAddress().equals(address)) {
                        ArrayList<String> dataPerOnePerson = new ArrayList<>();

                        dataPerOnePerson.add(person.getFirstName());
                        dataPerOnePerson.add(person.getLastName());
                        String id = person.getFirstName()+person.getLastName();
                        dataPerOnePerson.add(person.getAddress());
                        dataPerOnePerson.add(person.getPhone());
                        mapOfPersonsServedByOneStation.put(id, dataPerOnePerson);
                    }

            }
        }


        //trouver les adresses desservies par une station
    }
        return mapOfPersonsServedByOneStation;
    }
public boolean isThisPersonAChild(String firstLastName){
    for (MedicalrecordsModel model : arrayListMedicalrecords) {

        if (firstLastName.equals(model.getFirstName() + model.getLastName())) {
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

            String stringDateOfBirth = model.getBirthdate();


            LocalDate dateOfBirth = LocalDate.parse(stringDateOfBirth, formatter);
            LocalDate nineteenthBirthday = dateOfBirth.plusYears(19);
            if (today.isBefore(nineteenthBirthday)) {
                return true;
            }

        }
    }
    return false;
}
public HashMap<String, Integer> numberOfChildrenServedByOneStation() {
    int numberOfChildrenServedByOneStation = 0;
    for (String id : mapOfPersonsServedByOneStation.keySet()) {
        if (isThisPersonAChild(id)) {
            numberOfChildrenServedByOneStation += 1;
        }
    }
    mapOfChildrenServedByOneStation.put ("nombre d'enfants", numberOfChildrenServedByOneStation);
    return mapOfChildrenServedByOneStation;
}



public HashMap<String,HashMap> urlOne(String firestationNumber){
    firestationRepository.makeFirestationModels();
    this.arrayListFirestations = firestationRepository.getArrayListFirestations();
    medicalrecordsRepository.makeMedicalrecordsModels();;
    this.arrayListMedicalrecords = medicalrecordsRepository.getArrayListMedicalrecords();
    personRepository.makePersonModels();
    this.arrayListPersons=personRepository.getArrayListPersons();
    mapOfPersonsServedByOneStation(firestationNumber);
    numberOfChildrenServedByOneStation();
    HashMap<String, HashMap> resultUrlOne= new HashMap<>();
    resultUrlOne.put("persons", mapOfPersonsServedByOneStation);
    resultUrlOne.put("statistics", mapOfChildrenServedByOneStation);
    return resultUrlOne;

}
}



