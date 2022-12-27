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
public class UrlService2 {

    /*Cette url doit retourner une liste d'enfants (tout individu âgé de 18 ans ou moins) habitant à cette adresse.
La liste doit comprendre le prénom et le nom de famille de chaque enfant, son âge et une liste des autres
membres du foyer. S'il n'y a pas d'enfant, cette url peut renvoyer une chaîne vide.
*/

    //attributs de la classe url2 - RETURN hashmap1
    // hashmap 1 <String - Hashmap 2>
    //HASHMAP 2

    // KEY String "age" <-> VALUE int age
    // KEY String arraylist <-> VALUE arraylist : members of family

    //MODEL URLSERVICE 2 >
    //
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
    HashMap<String, ArrayList> resultUrl2 = new HashMap<>();//the key is first and last name concatenated

    //HashMap<String, ArrayList> family = new HashMap<>(); //the key is "members of family"


    public ArrayList<PersonModel> getPeopleInSameHouseHold(String address){
        ArrayList<PersonModel> peopleInSameHousehold = new ArrayList<>();

        for (PersonModel personModel : arrayListPersons) {
            ArrayList<String> firstAndLastName = new ArrayList<>();
            if (address.equals(personModel.getAddress())) {
                peopleInSameHousehold.add(personModel);
            }

        }
        return peopleInSameHousehold;
    }

    public HashMap<PersonModel, Integer> getListOfChildren(){
        HashMap<PersonModel, Integer> listOfChildrenAndAges= new HashMap<>();
        for (PersonModel personModel:arrayListPersons){
            String firstLastName = personModel.getFirstName()+personModel.getLastName();
            int age = urlService1.howOldIsThisPerson(firstLastName, arrayListMedicalrecords);
            if(age<19){
                listOfChildrenAndAges.put(personModel, age);

            }

        }
        return listOfChildrenAndAges;
    }

    public HashMap<String, ArrayList> urlTwo(String address) {
        if(root==null){
            root = makingModels.modelMaker();
        }

        firestationRepository.makeFirestationModels(root);
        this.arrayListFirestations = firestationRepository.getArrayListFirestations();
        medicalrecordsRepository.makeMedicalrecordsModels(root);

        this.arrayListMedicalrecords = medicalrecordsRepository.getArrayListMedicalrecords();
        personRepository.makePersonModels(root);
        this.arrayListPersons = personRepository.getArrayListPersons();

        HashMap<PersonModel, Integer> listOfChildrenAndAges= new HashMap<>();
        listOfChildrenAndAges = getListOfChildren();
        System.out.println("list of children and ages" + listOfChildrenAndAges);
        ArrayList<PersonModel> household = getPeopleInSameHouseHold(address);
            System.out.println("household is"+ household);
            for (PersonModel item : household) {

                if (listOfChildrenAndAges.containsKey(item)) {
                    String nameOfChild = item.getFirstName() + " " + item.getLastName();
                    System.out.println("name of child = "+ nameOfChild);
                    int ageOfChild = listOfChildrenAndAges.get(item);
                    System.out.println("age of child = " + ageOfChild);
                    HashMap<String, Integer> informationsAge = new HashMap<>(); //the key is "age"
                    informationsAge.put("age", ageOfChild);

                    System.out.println("household without child is "+ household);
                    ArrayList<String> namesOfOtherMembers = new ArrayList<>();
                    for (PersonModel otherMember : household) {
                        if(nameOfChild!= (otherMember.getFirstName()+ " "+ otherMember.getLastName())) {
                            namesOfOtherMembers.add(otherMember.getFirstName() + " " + otherMember.getLastName());
                        }
                    }
                    System.out.println("names of other members are "+ namesOfOtherMembers);
                    HashMap<String, ArrayList<String>> informationsFamily = new HashMap<>();
                    informationsFamily.put("household", namesOfOtherMembers);
                    ArrayList<HashMap> listOfInformations = new ArrayList<>();
                    listOfInformations.add(informationsAge);

                    listOfInformations.add(informationsFamily);
                    System.out.println ("listOf Informations " + listOfInformations);
                    resultUrl2.put(nameOfChild, listOfInformations);
                    System.out.println("informations put in result");
                }
            }
        System.out.println(resultUrl2);
        return resultUrl2;
    }


}
