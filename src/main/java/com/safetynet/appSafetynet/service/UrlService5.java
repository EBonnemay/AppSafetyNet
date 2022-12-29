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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Data
@Service
public class UrlService5 {


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
    @Autowired
    UrlService4 urlService4;
    ArrayList<PersonModel> arrayListPersons;
    ArrayList<FirestationModel> arrayListFirestations;
    ArrayList<MedicalrecordsModel> arrayListMedicalrecords;

    Any root;
    // HashMap<String, ArrayList> resultUrl5 = new HashMap<>();//the key is first and last name concatenated
    public HashMap<String, Object> urlFive(String stringNumbersOfFirestations){

        HashMap<String, Object> resultUrl5 = new HashMap<>();
        if(root==null){
            root = makingModels.modelMaker();
        }

        firestationRepository.makeFirestationModels(root);
        this.arrayListFirestations = firestationRepository.getArrayListFirestations();
        medicalrecordsRepository.makeMedicalrecordsModels(root);

        this.arrayListMedicalrecords = medicalrecordsRepository.getArrayListMedicalrecords();
        personRepository.makePersonModels(root);
        this.arrayListPersons = personRepository.getArrayListPersons();

        System.out.println("models are filled in");
        //System.out.println("list is "+numbersOfFirestations);
        List<String> numbersOfFirestations = Arrays.asList(stringNumbersOfFirestations.split(","));
        //boucler sur la liste des firestations
        for (String numberOfFirestation : numbersOfFirestations){
            System.out.println("number = "+numberOfFirestation);
            HashMap<String, Object>householdDataWithAddressesKeys = new HashMap<>();
            ArrayList<String> addressesServedByOneStation = firestationRepository.findAddressesServedByOneStation(numberOfFirestation, arrayListFirestations);

            for (String address : addressesServedByOneStation){
                HashMap<String, Object> householdDataWithNameKeys = urlService4.getHouseholdDataWithNameKey(address, arrayListMedicalrecords, arrayListPersons);

//PROBLEME ICI > APPELLE HOW OLD DU SERVICE 4 QUI UTILISE ARRAYLIST MEDICAL RECORDS DU SERVICE 4, QUI EST VIDE
//
                householdDataWithAddressesKeys.put(address, householdDataWithNameKeys);
                System.out.println("household "+householdDataWithAddressesKeys);

            }

            resultUrl5.put("firestation"+ numberOfFirestation, householdDataWithAddressesKeys);

        }
        // pour chaque firestation faire la liste des adresses (dans firestation repository : public ArrayList <String> findAddressesServedByOneStation(String numberOfStation){
        // pour chaque adresse récupérer un hashmap householdDataWithNameKey (bean de urlservice 4

        System.out.println(resultUrl5);
        return resultUrl5;
    }
}
