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
public class UrlService4 {

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
   // HashMap<String, ArrayList> resultUrl2 = new HashMap<>();//the key is first and last name concatenated
    public HashMap<String, Object> urlFour(String address){
        HashMap<String, Object> resultUrl4 = new HashMap<>();
        if(root==null){
            root = makingModels.modelMaker();
        }

        firestationRepository.makeFirestationModels(root);
        this.arrayListFirestations = firestationRepository.getArrayListFirestations();
        medicalrecordsRepository.makeMedicalrecordsModels(root);

        this.arrayListMedicalrecords = medicalrecordsRepository.getArrayListMedicalrecords();
        personRepository.makePersonModels(root);
        this.arrayListPersons = personRepository.getArrayListPersons();
        String numberOfStation = "";
        for(FirestationModel firestationModel: arrayListFirestations){

            if(address.equals(firestationModel.getAddress())){
                numberOfStation = firestationModel.getStation();

            }
        }
        HashMap<String, Object> householdDataWithNameKey =  getHouseholdDataWithNameKey(address, arrayListMedicalrecords, arrayListPersons);

        resultUrl4.put("firestation number of "+ address,numberOfStation );
        resultUrl4.put ("persons living at "+ address, householdDataWithNameKey );


        return resultUrl4;
    }

    public HashMap<String, Object> getHouseholdDataWithNameKey(String address, ArrayList<MedicalrecordsModel> arrayMedicalrecords, ArrayList<PersonModel> arrayPersons){
        ArrayList<PersonModel> arrayListHousehold = new ArrayList<>();
        HashMap<String, Object> householdDataWithNameKey = new HashMap<>();
        arrayListHousehold = personRepository.getPeopleInSameHouseHold(address, arrayPersons);
        for (PersonModel item : arrayListHousehold){ //pour chaque personModel dans la liste du foyer
            HashMap<String, Object> dataForOnePerson = new HashMap<>();//crée une hashmap vide "dataforoneperson"

            dataForOnePerson.put("phone", item.getPhone());
            String firstLastName = item.getFirstName()+ " "+item.getLastName();

            //int ageOfItem = medicalrecordsRepository.howOldIsThisPerson(firstLastName, arrayListMedicalrecords);

            //dataForOnePerson.put("age",ageOfItem );
            for( MedicalrecordsModel medicalFile : arrayMedicalrecords){
                if(firstLastName.equals(medicalFile.getFirstName()+" "+medicalFile.getLastName())){
                    String stringDateOfBirth = medicalFile.getBirthdate();
                    int age = medicalrecordsRepository.howOldIsThisPerson2(stringDateOfBirth);
                    dataForOnePerson.put ("age", age);
                    dataForOnePerson.put("medications", medicalFile.getMedications());
                    dataForOnePerson.put("allergies", medicalFile.getAllergies());


                    householdDataWithNameKey.put(item.getFirstName()+" "+item.getLastName(), dataForOnePerson);
                }
            }
        }
        return householdDataWithNameKey;
    }
}
