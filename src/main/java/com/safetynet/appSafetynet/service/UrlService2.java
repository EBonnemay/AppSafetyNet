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

    public HashMap<PersonModel, Integer> getListAndAgesOfChildren(){
        HashMap<PersonModel, Integer> listOfChildrenAndAges= new HashMap<>();
        //System.out.println("arraylistpersons vide? "+arrayListPersons.isEmpty());
        for (PersonModel personModel:arrayListPersons) { // pour chaque personne dans la liste
            String firstLastName = personModel.getFirstName() + " " + personModel.getLastName(); //récupère firsLastName
            System.out.println("test name "+ firstLastName);
            System.out.println("test dateOfBirth "+ personModel.getMedicalrecords().getBirthdate());
            for (MedicalrecordsModel medicalModel : arrayListMedicalrecords) { //pour chaque dossier médical aussi
                String id = medicalModel.getFirstName() + " "+ medicalModel.getLastName();// aussi
                if (firstLastName.equals(id)) {//si dossier correspond au nom de la perosnne
                    String stringDateOfBirth = medicalModel.getBirthdate();// récupère la date de naissance qui y figure
                    int age = medicalrecordsRepository.howOldIsThisPerson2(stringDateOfBirth);//calcule son age
                    if (age < 19) {//s'il est inférieur à 19
                        listOfChildrenAndAges.put(personModel, age);//place le dans une map avec le person model en clé
                    }
                }
            }
        }
        System.out.println(listOfChildrenAndAges);
        return listOfChildrenAndAges;
    }

    public HashMap<String, ArrayList> urlTwo(String address) {
        HashMap<String, ArrayList> resultUrl2 = new HashMap<>();
        String nameOfChild = "";
        if(root==null){
            root = makingModels.modelMaker();
        }

        firestationRepository.makeFirestationModels(root);
        this.arrayListFirestations = firestationRepository.getArrayListFirestations();
        medicalrecordsRepository.makeMedicalrecordsModels(root);

        this.arrayListMedicalrecords = medicalrecordsRepository.getArrayListMedicalrecords();
        personRepository.makePersonModels(root);
        this.arrayListPersons = personRepository.getArrayListPersons();

       // HashMap<PersonModel, Integer> listOfChildrenAndAges= getListAndAgesOfChildren();

        ArrayList<PersonModel> household = personRepository.getPeopleInSameHouseHold(address, arrayListPersons);

            for (PersonModel item : household) { //pour chaque personne dans le foyer
                if(item.getAge()<19){
                    nameOfChild = item.getFirstName()+" "+item.getLastName();
                    HashMap<String, Integer> informationsAge = new HashMap<>(); //3et crée un hashmap "age" > age
                    informationsAge.put("age", item.getAge());
                    ArrayList<String> namesOfOtherMembers = new ArrayList<>();//4crée une liste de strings "autres membres"

                    for (PersonModel otherMember : household) {//repasse en revue les personnes dans le foyer
                        if(!item.equals(otherMember)) {//s'il est faux que l'enfant est cette personne
                            namesOfOtherMembers.add(otherMember.getFirstName() + " " + otherMember.getLastName()); //ajoute le nom de cette personne à une liste
                        }
                    }
                    HashMap<String, ArrayList<String>> informationsFamily = new HashMap<>();
                    informationsFamily.put("household", namesOfOtherMembers);
                    ArrayList<HashMap> listOfInformations = new ArrayList<>();
                    listOfInformations.add(informationsAge);
                    listOfInformations.add(informationsFamily);
                    resultUrl2.put(nameOfChild, listOfInformations);
                }
            }

        return resultUrl2;
    }


}
