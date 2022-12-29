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
public class UrlService6 {

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
    public HashMap<String, HashMap> urlSix(String firstName, String lastName) {

        HashMap<String, HashMap> resultUrl6 = new HashMap<>();
        if (root == null) {
            root = makingModels.modelMaker();
        }

        firestationRepository.makeFirestationModels(root);
        this.arrayListFirestations = firestationRepository.getArrayListFirestations();
        medicalrecordsRepository.makeMedicalrecordsModels(root);

        this.arrayListMedicalrecords = medicalrecordsRepository.getArrayListMedicalrecords();
        personRepository.makePersonModels(root);
        this.arrayListPersons = personRepository.getArrayListPersons();

        System.out.println("models are filled in");

        for (PersonModel person : arrayListPersons) {
            if (firstName.equals(person.getFirstName()) && lastName.equals(person.getLastName())) {
                HashMap<String, Object> generalInfo = new HashMap<>();
                HashMap<String, Object> medicalInfo = new HashMap<>();
                generalInfo.put("address", person.getAddress());
                generalInfo.put("age", person.getAge());
                generalInfo.put("mail", person.getEmail());
                medicalInfo.put("medications", person.getMedicalrecords().getMedications());
                medicalInfo.put("allergies", person.getMedicalrecords().getAllergies());
                generalInfo.put("medical history", medicalInfo);
                resultUrl6.put(person.getFirstName() + " " + person.getLastName(), generalInfo);

            }
        }
        return resultUrl6;
    }

}
