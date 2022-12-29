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
public class UrlService3 {
    @Autowired
    MakingModels makingModels;
    @Autowired
    FirestationRepository firestationRepository;
    @Autowired
    MedicalrecordsRepository medicalrecordsRepository;
    @Autowired
    PersonRepository personRepository;

    @Autowired
    UrlService1 urlService1;
    ArrayList<PersonModel> arrayListPersons;
    ArrayList<FirestationModel> arrayListFirestations;
    ArrayList<MedicalrecordsModel> arrayListMedicalrecords;

    Any root;
    //ArrayList<String> resultUrl3 = new ArrayList<>();//the key is first and last name concatenated

    //HashMap<String, ArrayList> family = new HashMap<>(); //the key is "members of family"

    public HashMap<String, ArrayList<String>> urlThree(String numberOfStation) {
        HashMap<String, ArrayList<String>> resultUrl3 = new HashMap<>();
        ArrayList<String> phoneNumbersCoveredByOneStation = new ArrayList<>();
        if(root==null){
            root = makingModels.modelMaker();
        }

        firestationRepository.makeFirestationModels(root);
        this.arrayListFirestations = firestationRepository.getArrayListFirestations();
        medicalrecordsRepository.makeMedicalrecordsModels(root);

        this.arrayListMedicalrecords = medicalrecordsRepository.getArrayListMedicalrecords();
        personRepository.makePersonModels(root);
        this.arrayListPersons = personRepository.getArrayListPersons();

        ArrayList <String> listOfAddressesServedByOneStation = firestationRepository.findAddressesServedByOneStation(numberOfStation, arrayListFirestations);
        for(String address:listOfAddressesServedByOneStation){
            for(PersonModel person : arrayListPersons ){
                if (address.equals(person.getAddress())){
                    if(!phoneNumbersCoveredByOneStation.contains(person.getPhone())) {
                        phoneNumbersCoveredByOneStation.add(person.getPhone());
                    }
                }
            }
        }
        resultUrl3.put("Phone numbers for firestation " + numberOfStation, phoneNumbersCoveredByOneStation);
        return resultUrl3;
        }
}
