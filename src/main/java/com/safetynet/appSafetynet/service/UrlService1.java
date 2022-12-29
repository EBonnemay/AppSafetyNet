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


   // mapOfChildrenServedByOneStation = new HashMap<String, Integer>();


    public HashMap<String, ArrayList> getMapOfPersonsServedByOneStation(String numberOfStation){
        System.out.println("numberofstation is "+ numberOfStation); //print 1
        HashMap<String, ArrayList> mapOfPersonsServedByOneStation = new HashMap<>();

        System.out.println("arrayListFirestationempty? "+arrayListFirestations.isEmpty());//print false

        for (FirestationModel element : arrayListFirestations) {
            String numberOfStationInData = element.getStation();
            System.out.println(element.getStation());//print la liste des numéros de stations
            if (numberOfStation.equals(numberOfStationInData)) {
                System.out.println("match");//NE RENTRE PAS DANS LA CONDITION
                String address = element.getAddress();
                System.out.println("arraylistpersonsempty? "+arrayListPersons.isEmpty()); //NE RENTRE PAS DANS LA CONDITION
                for (PersonModel person : arrayListPersons) {
                    System.out.println("arrayListPersons empty? " + arrayListPersons.isEmpty()); //NE RENTRE PAS DANS LA BOUCLE
                    if (person.getAddress().equals(address)) {
                        ArrayList<String> dataPerOnePerson = new ArrayList<>();

                        dataPerOnePerson.add(person.getFirstName());
                        dataPerOnePerson.add(person.getLastName());
                        String id = person.getFirstName() + " " + person.getLastName();
                        System.out.println(id);
                        dataPerOnePerson.add(person.getAddress());
                        dataPerOnePerson.add(person.getPhone());
                        mapOfPersonsServedByOneStation.put(id, dataPerOnePerson);
                    }
                }
            }
        }


        //trouver les adresses desservies par une station

        System.out.println(mapOfPersonsServedByOneStation.size());//print size = 0
        return mapOfPersonsServedByOneStation;
    }



public int getNumberOfChildrenServedByOneStation(HashMap<String, ArrayList> mapOfPersonsServedByOneStation) {
    int numberOfChildrenServedByOneStation = 0;

    for (String id : mapOfPersonsServedByOneStation.keySet()) {//pour chaque nom de personne servie par une station

        for (MedicalrecordsModel model : arrayListMedicalrecords) {//passe en revue les dossiers médicaux
            //System.out.println("arraymedicalrecordsempty? "+ arrayListMedicalrecords.isEmpty());
            //System.out.println(model.toString());
            String modelsName = model.getFirstName()+" "+model.getLastName();//en notant leurs propriétaires
            if (id.equals(modelsName)) {//si le propriétaire porte le nom de la personne servie par une station
                String dateOfBirth = model.getBirthdate();//conserve la date de naissance en string

                int age = medicalrecordsRepository.howOldIsThisPerson2(dateOfBirth);
                if (age < 19) {
                    numberOfChildrenServedByOneStation += 1;
                    System.out.println("number of children is"+ numberOfChildrenServedByOneStation);
                }
            }
        }
    }

    return numberOfChildrenServedByOneStation;
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

    //System.out.println("firestation number in main function "+ firestationNumber);// print 1
    HashMap<String, ArrayList>  mapOfPersonsServedByOneStation = getMapOfPersonsServedByOneStation(firestationNumber);
    int totalNumber = mapOfPersonsServedByOneStation.size();
    //System.out.println("numberOfPersonsFor1FN = "+mapOfPersonsServedByOneStation.size()); //print0

    int numberOfChildren = getNumberOfChildrenServedByOneStation(mapOfPersonsServedByOneStation);//print 2 avant return
    HashMap<String, Integer> mapNumbersOfChildrenAndAdults = new HashMap<>();
    mapNumbersOfChildrenAndAdults.put("number of children", numberOfChildren);
    mapNumbersOfChildrenAndAdults.put("number of adults", totalNumber-numberOfChildren);

    HashMap<String, HashMap> resultUrlOne= new HashMap<>();
    resultUrlOne.put("persons", mapOfPersonsServedByOneStation );
    resultUrlOne.put("statistics", mapNumbersOfChildrenAndAdults);

    return resultUrlOne;


}
}



