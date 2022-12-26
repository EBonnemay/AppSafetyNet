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

import java.util.ArrayList;
import java.util.HashMap;
@Data
@Service
public class UrlService2 {
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

}
