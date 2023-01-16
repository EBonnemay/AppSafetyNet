package com.safetynet.appSafetynet.service;

import com.jsoniter.any.Any;
import com.safetynet.appSafetynet.model.*;
import com.safetynet.appSafetynet.model.dto.*;
import com.safetynet.appSafetynet.repository.IFirestationRepository;
import com.safetynet.appSafetynet.repository.IMakingModels;
import com.safetynet.appSafetynet.repository.IMedicalrecordsRepository;
import com.safetynet.appSafetynet.repository.IPersonRepository;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Data
@Service
public class UrlService implements IUrlService{
    @Autowired
    IFirestationRepository firestationRepository;
    @Autowired
    IMedicalrecordsRepository medicalrecordsRepository;
    @Autowired
    IPersonRepository personRepository;

    @Autowired
    IMakingModels makingModels;

    Any root;
    ListOfFirestationModels listOfFirestationModels;
    ListOfPersonModels listOfPersonModels;
    ListOfMedicalrecordsModels listOfMedicalrecordsModels;
   ArrayList<PersonModel> arrayListPersons;
   ArrayList<FirestationModel> arrayListFirestations;
   ArrayList<MedicalrecordsModel> arrayListMedicalrecords;
    static final Logger logger = LogManager.getLogger();

    public UrlService(){

    }
   public void setUp(){
       if (root ==null) {
           root = makingModels.modelMaker("classpath:data.json");
       }

       listOfFirestationModels = firestationRepository.findAll();
       if(listOfFirestationModels.getListOfFirestationModels().isEmpty()){
           listOfFirestationModels = firestationRepository.fillInFirestationModels(root);
       }

       listOfMedicalrecordsModels = medicalrecordsRepository.findAll();
       if(listOfMedicalrecordsModels.getListOfMedicalrecordsModels().isEmpty()){
           listOfMedicalrecordsModels= medicalrecordsRepository.fillInMedicalrecordsModels(root);
       }

       listOfPersonModels = personRepository.findAll();
       if(listOfPersonModels.getListOfPersonModels().isEmpty()){
           listOfPersonModels = personRepository.fillInPersonModels(root);
       }

   }

public ListOfPersonsCoveredByAFirestationUrl1 urlOne(String firestationNumber){
        logger.info("url 1 returns the list of persons covered by station {} as well as their addresses, phone numbers, and the global number of children and adults covered by this firestation ", firestationNumber);
       setUp();
       ListOfPersonsCoveredByAFirestationUrl1 listOfPersonsCoveredByAFirestationUrl1 = new ListOfPersonsCoveredByAFirestationUrl1();
       int numberOfChildren = 0;
       int numberOfAdults = 0;
       int match = 0;
       for (FirestationModel firestationModel: listOfFirestationModels.getListOfFirestationModels()){ //pour chaque firestation

           if(firestationModel.getStation().equals(firestationNumber)){ //si le n° de firestation est le même que le param
               match = 1;
               String address = firestationModel.getAddress(); // j'appelle "adresse" l'adresse correspondante
               for(PersonModel personModel : listOfPersonModels.getListOfPersonModels()){
                   //int age = -1;//pour chaque personne
                   if(address.equals(personModel.getAddress())){ //si elle habite à "adressee
                       String firstName = personModel.getFirstName();
                       String lastName = personModel.getLastName();
                       String phone = personModel.getPhone();//je récupère ses coordonnées
                       PersonCoveredByAFirestationForUrl1 personCoveredByAFirestationForUrl1 = new PersonCoveredByAFirestationForUrl1(firstName, lastName, address, phone);
                       if(!listOfPersonsCoveredByAFirestationUrl1.getListOfPersonsCoveredByFirestation().contains(personCoveredByAFirestationForUrl1)){
                            listOfPersonsCoveredByAFirestationUrl1.getListOfPersonsCoveredByFirestation().add(personCoveredByAFirestationForUrl1);
                            int age = personRepository.howOldIsThisPerson(personModel.getMedicalrecordsModel().getBirthdate());
                            if(age<19) {
                                numberOfChildren++;
                            }if(age>=19) {
                                numberOfAdults++;
                            }
                       }
                   }
               }
           }

       }
        if(match ==0){
            logger.error("the firestation required was not found in the data");
            throw new RuntimeException("unsuccessful request of url 1");

        }
       listOfPersonsCoveredByAFirestationUrl1.setNumberOfAdults(numberOfAdults);
       listOfPersonsCoveredByAFirestationUrl1.setNumberOfChildren(numberOfChildren);


       return listOfPersonsCoveredByAFirestationUrl1;


}
    public ListOfChildrenAndTheHouseholdWithOneAddressUrl2 urlTwo(String address) throws RuntimeException {
        logger.info("url 2 returns list of children living at {} as well as their ages and each one's household ", address);

        setUp();
        ListOfChildrenAndTheHouseholdWithOneAddressUrl2 listOfChildrenAndTheHouseholdWithOneAddressUrl2 = new ListOfChildrenAndTheHouseholdWithOneAddressUrl2();
        List<PersonModel> householdPersonModel = personRepository.getPeopleInSameHouseHold(address, listOfPersonModels);
        for(PersonModel personModel : householdPersonModel){
            if(personRepository.howOldIsThisPerson(personModel.getMedicalrecordsModel().getBirthdate())<19){
                ChildAndHisHouseholdForUrl2 childAndHisHouseholdForUrl2 = new ChildAndHisHouseholdForUrl2();
                childAndHisHouseholdForUrl2.setFirstNameOfChild(personModel.getFirstName());
                childAndHisHouseholdForUrl2.setLastNameOfChild(personModel.getLastName());
                childAndHisHouseholdForUrl2.setAge(personRepository.howOldIsThisPerson(personModel.getMedicalrecordsModel().getBirthdate()));
                List <String> otherMembersString = new ArrayList<>();
                for(PersonModel personModel2 : householdPersonModel){
                    if(!((childAndHisHouseholdForUrl2.getFirstNameOfChild()+" "+childAndHisHouseholdForUrl2.getLastNameOfChild()).equals(personModel2.getFirstName()+" "+ personModel2.getLastName()))){
                    otherMembersString.add(personModel2.getFirstName()+" "+ personModel2.getLastName());
                    }
                }
                childAndHisHouseholdForUrl2.setListOfOtherMembers(otherMembersString);
                listOfChildrenAndTheHouseholdWithOneAddressUrl2.getListOfChidrenAndHouseholdWithOneAddress().add(childAndHisHouseholdForUrl2);

            }
        }
        return listOfChildrenAndTheHouseholdWithOneAddressUrl2;
    }

    public HashMap<String, ArrayList<String>> urlThree(String numberOfStation) {
        logger.info("url 3 returns the list of phone numbers of all the people covered by firestation number {}", numberOfStation);
        setUp();
        HashMap<String, ArrayList<String>> mapResultUrl3 = new HashMap<>();
        ArrayList<String> phoneNumbersCoveredByOneStation = new ArrayList<>();
        int match = 0;


        ArrayList <String> listOfAddressesServedByOneStation = firestationRepository.findAddressesServedByOneStation(numberOfStation, listOfFirestationModels);
        for(String address:listOfAddressesServedByOneStation){//setUp nécessaire?oui
            for(PersonModel person : listOfPersonModels.getListOfPersonModels() ){
                if (address.equals(person.getAddress())){
                    match = 1;
                    if(!phoneNumbersCoveredByOneStation.contains(person.getPhone())) {
                        phoneNumbersCoveredByOneStation.add(person.getPhone());
                    }
                }
            }
        }
        if(match ==0){
            logger.error("the number of station required was not found in the data");
            throw new RuntimeException("unsuccessful request of url 3");

        }
        mapResultUrl3.put("Phone numbers for firestation " + numberOfStation, phoneNumbersCoveredByOneStation);
        return mapResultUrl3;
    }
   public HouseholdUrl4 urlFour(String address) throws RuntimeException{
       logger.info("url 4 returns names, ages, phone and medical data of persons living at {}, as well as the corresponding station number", address);
        setUp();
        HouseholdUrl4 householdUrl4 = new HouseholdUrl4();
       List<PersonModel> householdPersonModelForUrls = personRepository.getPeopleInSameHouseHold(address, listOfPersonModels);//setUp nécessaire?
       for(PersonModel person : householdPersonModelForUrls){
           PersonInfoForUrl4And5 personInfoForUrl4And5 = new PersonInfoForUrl4And5();
           MedicalRecordsForUrl4And5 medicalRecordsForUrl4And5 = new MedicalRecordsForUrl4And5();
           medicalRecordsForUrl4And5.setMedications(person.getMedicalrecordsModel().getMedications());
           medicalRecordsForUrl4And5.setListOfAllergies(person.getMedicalrecordsModel().getAllergies());
           personInfoForUrl4And5.setFirstName(person.getFirstName());
           personInfoForUrl4And5.setLastName(person.getLastName());
           personInfoForUrl4And5.setPhoneNumber(person.getPhone());
           personInfoForUrl4And5.setAge(personRepository.howOldIsThisPerson(person.getMedicalrecordsModel().getBirthdate()));
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

    public ListOfHouseholdsCoveredByAFirestationUrl5 urlFive(String stringNumbersOfFirestations) throws RuntimeException{
        logger.info("url 5 returns the list of households covered by firestation or firestations {} ; each one includes names, ages, phone and medical data of persons in the household", stringNumbersOfFirestations);

        setUp();// pour la deuxième fois!
        ListOfHouseholdsCoveredByAFirestationUrl5 listOfHouseholdsCoveredByAFirestationUrl5 = new ListOfHouseholdsCoveredByAFirestationUrl5();

        List<String> numbersOfFirestations = Arrays.asList(stringNumbersOfFirestations.split(","));
        for (String numberOfFirestation : numbersOfFirestations){

            ArrayList<String> addressesServedByOneStation = firestationRepository.findAddressesServedByOneStation(numberOfFirestation, listOfFirestationModels);

            for (String address : addressesServedByOneStation){
                HouseholdUrl4 householdUrl4 = urlFour(address);

                listOfHouseholdsCoveredByAFirestationUrl5.getListOfHouseholdsCoveredByAFirestationUrl5().add(householdUrl4);
            }

        }

        return listOfHouseholdsCoveredByAFirestationUrl5;
    }

    public ListOfPersonsInfosUrl6 urlSix (String firstName, String lastName) {
        logger.info("url 6 returns information for {} {} : address, age, mail, medical data", firstName, lastName);
        int match = 0;
        setUp();
        ListOfPersonsInfosUrl6 listOfPersonsInfosUrl6 = new ListOfPersonsInfosUrl6();

        for (PersonModel person : listOfPersonModels.getListOfPersonModels()) {
            if (firstName.equals(person.getFirstName()) && lastName.equals(person.getLastName())) {
                match = 1;
                PersonInfoForUrl6 personInfoForUrl6 = new PersonInfoForUrl6();
                personInfoForUrl6.setFirstName(firstName);
                personInfoForUrl6.setLastName(lastName);
                personInfoForUrl6.setAddress(person.getAddress());
                personInfoForUrl6.setAge(personRepository.howOldIsThisPerson(person.getMedicalrecordsModel().getBirthdate()));
                personInfoForUrl6.setMailAddress(person.getEmail());
                List<String> listOfAllergies = person.getMedicalrecordsModel().getAllergies();
                HashMap<String, String> listOfMeds = person.getMedicalrecordsModel().getMedications();
                personInfoForUrl6.getMedicalRecordsForUrl4And5().setMedications(listOfMeds);
                personInfoForUrl6.getMedicalRecordsForUrl4And5().setListOfAllergies(listOfAllergies);
                listOfPersonsInfosUrl6.getListOfPersonInfoForUrl6().add(personInfoForUrl6);
            }
        }
        if( match == 0){
            logger.error("the name required is not in the data.");
            throw new RuntimeException("name not found");
        }
        return listOfPersonsInfosUrl6;
    }

    public ArrayList<String> urlSeven(String city) {
        logger.info("url 7 returns a list of emails of the inhabitants of {}", city);
        int match = 0;
        setUp();
        ArrayList<String> resultUrl7 = new ArrayList<>();
        for (PersonModel person : listOfPersonModels.getListOfPersonModels()){
            if(person.getCity().equals(city)){
                match = 1;
                resultUrl7.add(person.getEmail());
            }

        }
        if( match == 0){
            logger.error("the city required is not in the data.");
            throw new RuntimeException("city not found");
        }
        return resultUrl7;

    }
}





