package com.safetynet.appSafetynet.service;

import com.jsoniter.any.Any;
import com.safetynet.appSafetynet.model.FirestationModel;
import com.safetynet.appSafetynet.model.MedicalrecordsModel;
import com.safetynet.appSafetynet.model.PersonModel;
import com.safetynet.appSafetynet.repository.FirestationRepository;
import com.safetynet.appSafetynet.repository.MakingModels;
import com.safetynet.appSafetynet.repository.MedicalrecordsRepository;
import com.safetynet.appSafetynet.repository.PersonRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    MakingModels makingModels;

    Any root;

   ArrayList<PersonModel> arrayListPersons;
   ArrayList<FirestationModel> arrayListFirestations;
   ArrayList<MedicalrecordsModel> arrayListMedicalrecords;

   int numberOfAdults2;
   int numberOfChildren2;


   // mapOfChildrenServedByOneStation = new HashMap<String, Integer>();


    public HashMap<String, ArrayList> getMapOfPersonsServedByOneStation(String numberOfStation){

        HashMap<String, ArrayList> mapOfPersonsServedByOneStation = new HashMap<>();


        for (FirestationModel element : arrayListFirestations) {
            if (numberOfStation.equals(element.getStation())){
                String address = element.getAddress();
                for (PersonModel person : arrayListPersons) {
                    if (person.getAddress().equals(address)) {
                        ArrayList<String> dataPerOnePerson = new ArrayList<>();
                        String id = person.getFirstName() + " " + person.getLastName();
                        dataPerOnePerson.add(person.getAddress());
                        dataPerOnePerson.add(person.getPhone());
                        mapOfPersonsServedByOneStation.put(id, dataPerOnePerson);
                        if(person.getAge()>18) {
                            numberOfAdults2 += 1;
                        }
                        else{
                            numberOfChildren2 +=1;
                        }
                    }
                }
            }
        }


        //trouver les adresses desservies par une station

        System.out.println(mapOfPersonsServedByOneStation.size());//print size = 0
        return mapOfPersonsServedByOneStation;
    }



public HashMap<String,HashMap> urlOne(String firestationNumber){
    if (root ==null){
        root = makingModels.modelMaker();
    }
    firestationRepository.makeFirestationModels(root);
    this.arrayListFirestations = firestationRepository.getArrayListFirestations();

    medicalrecordsRepository.makeMedicalrecordsModels(root);
    this.arrayListMedicalrecords = medicalrecordsRepository.getArrayListMedicalrecords();


    personRepository.makePersonModels(root);
    this.arrayListPersons=personRepository.getArrayListPersons();


    HashMap<String, ArrayList>  mapOfPersonsServedByOneStation = getMapOfPersonsServedByOneStation(firestationNumber);

    HashMap<String, Integer> mapNumbersOfChildrenAndAdults = new HashMap<>();
    mapNumbersOfChildrenAndAdults.put("number of children", numberOfChildren2);
    mapNumbersOfChildrenAndAdults.put("number of adults", numberOfAdults2);

    HashMap<String, HashMap> resultUrlOne= new HashMap<>();
    resultUrlOne.put("persons", mapOfPersonsServedByOneStation );
    resultUrlOne.put("statistics", mapNumbersOfChildrenAndAdults);

    return resultUrlOne;


}
}



