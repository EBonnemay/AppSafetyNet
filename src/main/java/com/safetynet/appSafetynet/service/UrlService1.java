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

import java.time.LocalDate;
import java.time.Period;
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

    @Autowired
    MakingModels makingModels;

    Any root;

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
public int howOldIsThisPerson(String firstLastName, ArrayList <MedicalrecordsModel> list ){
    try{
        System.out.println("in function how old");

        for (MedicalrecordsModel model : list) {
            System.out.println(model.toString());
            String modelsName = model.getFirstName()+model.getLastName();
            if (firstLastName.equals(modelsName)) {
                LocalDate today = LocalDate.now();
                System.out.println("today"+today);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                System.out.println("formatter ok");
                String stringDateOfBirth = model.getBirthdate();
                System.out.println("stringdateofBirth" +stringDateOfBirth);


                LocalDate dateOfBirth = LocalDate.parse(stringDateOfBirth, formatter);
                System.out.println("dateOfBirth"+ dateOfBirth);
                //LocalDate nineteenthBirthday = dateOfBirth.plusYears(19);
                int age = Period.between(dateOfBirth, today).getYears();
                System.out.println("age"+ age);
                return age;
            }
        }
    }catch(Exception e){
        throw new RuntimeException("not found person");
        }
    return -1;
    }



public HashMap<String, Integer> numberOfChildrenServedByOneStation() {
    int numberOfChildrenServedByOneStation = 0;
    for (String id : mapOfPersonsServedByOneStation.keySet()) {

        int age = howOldIsThisPerson(id, arrayListMedicalrecords);
        if(age<19) {
            numberOfChildrenServedByOneStation += 1;
        }
    }
    mapOfChildrenServedByOneStation.put ("nombre d'enfants", numberOfChildrenServedByOneStation);
    return mapOfChildrenServedByOneStation;
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
    mapOfPersonsServedByOneStation(firestationNumber);
    numberOfChildrenServedByOneStation();
    HashMap<String, HashMap> resultUrlOne= new HashMap<>();
    resultUrlOne.put("persons", mapOfPersonsServedByOneStation);
    resultUrlOne.put("statistics", mapOfChildrenServedByOneStation);
    return resultUrlOne;

}
}



