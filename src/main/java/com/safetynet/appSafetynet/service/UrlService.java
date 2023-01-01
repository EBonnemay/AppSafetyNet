package com.safetynet.appSafetynet.service;

import com.jsoniter.any.Any;
import com.safetynet.appSafetynet.model.*;
import com.safetynet.appSafetynet.model.dto.*;
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
public class UrlService {
    @Autowired
    FirestationRepository firestationRepository;
    @Autowired
    MedicalrecordsRepository medicalrecordsRepository;
    @Autowired
    PersonRepository personRepository;

    @Autowired
    MakingModels makingModels;

    Any root;
    ListOfFirestationModels listOfFirestationModels;
    ListOfPersonModels listOfPersonModels;
    ListOfMedicalrecordsModels listOfMedicalrecordsModels;
   ArrayList<PersonModel> arrayListPersons;
   ArrayList<FirestationModel> arrayListFirestations;
   ArrayList<MedicalrecordsModel> arrayListMedicalrecords;

   int numberOfAdults;
   int numberOfChildren;

   public void setUp(){
       if (root ==null) {
           root = makingModels.modelMaker();
       }

       listOfFirestationModels = firestationRepository.fillInFirestationModels(root);


       listOfMedicalrecordsModels = medicalrecordsRepository.fillInMedicalrecordsModels(root);
       //this.arrayListMedicalrecords = medicalrecordsRepository.getArrayListMedicalrecords();
       listOfPersonModels = personRepository.fillInPersonModels(root);
      // personRepository.makePersonModels(root);
       //this.arrayListPersons = personRepository.getArrayListPersons();

   }

public ListOfPersonsCoveredByAFirestationUrl1 urlOne(String firestationNumber){
       setUp();
       ListOfPersonsCoveredByAFirestationUrl1 listOfPersonsCoveredByAFirestationUrl1 = new ListOfPersonsCoveredByAFirestationUrl1();
       int numberOfChildren = 0;
       int numberOfAdults = 0;

       for (FirestationModel firestationModel: listOfFirestationModels.getListOfFirestationModels()){

           if(firestationModel.getStation().equals(firestationNumber)){
               String address = firestationModel.getAddress();
               for(PersonModel personModel : listOfPersonModels.getListOfPersonModels()){
                   if(address.equals(personModel.getAddress())){
                       String firstName = personModel.getFirstName();
                       String lastName = personModel.getLastName();
                       String phone = personModel.getPhone();
                       PersonCoveredByAFirestationForUrl1 personCoveredByAFirestationForUrl1 = new PersonCoveredByAFirestationForUrl1(firstName, lastName, address, phone);
                       if(!listOfPersonsCoveredByAFirestationUrl1.getListOfPersonsCoveredByFirestation().contains(personCoveredByAFirestationForUrl1)){
                            listOfPersonsCoveredByAFirestationUrl1.getListOfPersonsCoveredByFirestation().add(personCoveredByAFirestationForUrl1);
                            int age = personRepository.howOldIsThisPerson(personModel.getDateOfBirth());
                            if(age<19) {
                                numberOfChildren++;
                            }else {
                                numberOfAdults++;
                            }
                       }
                   }
               }
           }
       }
       listOfPersonsCoveredByAFirestationUrl1.setNumberOfAdults(numberOfAdults);
       listOfPersonsCoveredByAFirestationUrl1.setNumberOfChildren(numberOfChildren);
       return listOfPersonsCoveredByAFirestationUrl1;

}
    public ListOfChildrenAndTheHouseholdWithOneAddressUrl2 urlTwo(String address) {
        setUp();
        ListOfChildrenAndTheHouseholdWithOneAddressUrl2 listOfChildrenAndTheHouseholdWithOneAddressUrl2 = new ListOfChildrenAndTheHouseholdWithOneAddressUrl2();
        List<PersonModel> householdPersonModels = personRepository.getPeopleInSameHouseHold(address, listOfPersonModels);//setUp nécessaire???
        for(PersonModel personModel : householdPersonModels){
            if(personModel.getAge()<19){
                ChildAndHisHouseholdForUrl2 childAndHisHouseholdForUrl2 = new ChildAndHisHouseholdForUrl2();
                childAndHisHouseholdForUrl2.setFirstNameOfChild(personModel.getFirstName());
                childAndHisHouseholdForUrl2.setLastNameOfChild(personModel.getLastName());
                childAndHisHouseholdForUrl2.setAge(personModel.getAge());
                List <String> otherMembersString = new ArrayList<>();
                for(PersonModel personModel2 : householdPersonModels ){
                    if(!((childAndHisHouseholdForUrl2.getFirstNameOfChild()+" "+childAndHisHouseholdForUrl2.getLastNameOfChild()).equals(personModel2.getFirstName()+" "+personModel2.getLastName()))){
                    otherMembersString.add(personModel2.getFirstName()+" "+personModel2.getLastName());
                    }
                }
                childAndHisHouseholdForUrl2.setListOfOtherMembers(otherMembersString);
                listOfChildrenAndTheHouseholdWithOneAddressUrl2.getListOfChidrenAndHouseholdWithOneAddress().add(childAndHisHouseholdForUrl2);

            }
        }
        return listOfChildrenAndTheHouseholdWithOneAddressUrl2;
    }

    public HashMap<String, ArrayList<String>> urlThree(String numberOfStation) {
        setUp();
        HashMap<String, ArrayList<String>> mapResultUrl3 = new HashMap<>();
        ArrayList<String> phoneNumbersCoveredByOneStation = new ArrayList<>();



        ArrayList <String> listOfAddressesServedByOneStation = firestationRepository.findAddressesServedByOneStation(numberOfStation, listOfFirestationModels);
        for(String address:listOfAddressesServedByOneStation){//setUp nécessaire?
            for(PersonModel person : listOfPersonModels.getListOfPersonModels() ){
                if (address.equals(person.getAddress())){
                    if(!phoneNumbersCoveredByOneStation.contains(person.getPhone())) {
                        phoneNumbersCoveredByOneStation.add(person.getPhone());
                    }
                }
            }
        }
        mapResultUrl3.put("Phone numbers for firestation " + numberOfStation, phoneNumbersCoveredByOneStation);
        return mapResultUrl3;
    }
   public HouseholdUrl4 urlFour(String address) {

        setUp();
        HouseholdUrl4 householdUrl4 = new HouseholdUrl4();
       List<PersonModel> householdPersonModels = personRepository.getPeopleInSameHouseHold(address, listOfPersonModels);//setUp nécessaire?
       for(PersonModel person : householdPersonModels ){
           PersonInfoForUrl4And5 personInfoForUrl4And5 = new PersonInfoForUrl4And5();
           MedicalRecordsForUrl4And5 medicalRecordsForUrl4And5 = new MedicalRecordsForUrl4And5();
           medicalRecordsForUrl4And5.setMedications(person.getMap0fMedications());
           medicalRecordsForUrl4And5.setListOfAllergies(person.getListOfAllergies());
           personInfoForUrl4And5.setFirstName(person.getFirstName());
           personInfoForUrl4And5.setLastName(person.getLastName());
           personInfoForUrl4And5.setPhoneNumber(person.getPhone());
           personInfoForUrl4And5.setAge(person.getAge());
           personInfoForUrl4And5.setMedicalRecordsForUrl4And5(medicalRecordsForUrl4And5);
           householdUrl4.getListOfPersons().add(personInfoForUrl4And5);


       }
       householdUrl4.setAddress(address);
       for(FirestationModel firestationModel : listOfFirestationModels.getListOfFirestationModels()){
           if(firestationModel.getAddress().equals(address)){
               householdUrl4.setFirestationNumber(firestationModel.getStation());
           }
       }

        return householdUrl4;

   }

    public ListOfHouseholdsCoveredByAFirestationUrl5 urlFive(String stringNumbersOfFirestations){

        setUp();// pour la deuxième fois!
        ListOfHouseholdsCoveredByAFirestationUrl5 listOfHouseholdsCoveredByAFirestationUrl5 = new ListOfHouseholdsCoveredByAFirestationUrl5();

        List<String> numbersOfFirestations = Arrays.asList(stringNumbersOfFirestations.split(","));
        for (String numberOfFirestation : numbersOfFirestations){
            //System.out.println("number = "+numberOfFirestation);
            //HashMap<String, Object>householdDataWithAddressesKeys = new HashMap<>();
            ArrayList<String> addressesServedByOneStation = firestationRepository.findAddressesServedByOneStation(numberOfFirestation, listOfFirestationModels);

            for (String address : addressesServedByOneStation){
                HouseholdUrl4 householdUrl4 = urlFour(address);

                listOfHouseholdsCoveredByAFirestationUrl5.getListOfHouseholdsCoveredByAFirestationUrl5().add(householdUrl4);
            }

        }
        return listOfHouseholdsCoveredByAFirestationUrl5;
    }

    public PersonInfoUrl6 urlSix (String firstName, String lastName) {
        setUp();
        PersonInfoUrl6 personInfoUrl6 = new PersonInfoUrl6();

        for (PersonModel person : listOfPersonModels.getListOfPersonModels()) {
            if (firstName.equals(person.getFirstName()) && lastName.equals(person.getLastName())) {
                personInfoUrl6.setFirstName(firstName);
                personInfoUrl6.setLastName(lastName);
                personInfoUrl6.setAddress(person.getAddress());
                personInfoUrl6.setAge(person.getAge());
                personInfoUrl6.setMailAddress(person.getEmail());
                List<String> listOfAllergies = person.getListOfAllergies();
                HashMap<String, String> listOfMeds = person.getMap0fMedications();
                personInfoUrl6.getMedicalRecordsForUrl4And5().setMedications(listOfMeds);
                personInfoUrl6.getMedicalRecordsForUrl4And5().setListOfAllergies(listOfAllergies);

            }
        }
        return personInfoUrl6;
    }

    public ArrayList<String> urlSeven(String city) {

        setUp();
        ArrayList<String> resultUrl7 = new ArrayList<>();


        for (PersonModel person :listOfPersonModels.getListOfPersonModels()){
            if(person.getCity().equals(city)){
                resultUrl7.add(person.getEmail());
            }

        }

        return resultUrl7;

    }
}





