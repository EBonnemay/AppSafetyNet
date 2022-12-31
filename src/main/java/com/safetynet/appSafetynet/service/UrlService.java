package com.safetynet.appSafetynet.service;

import com.jsoniter.any.Any;
import com.safetynet.appSafetynet.model.*;
import com.safetynet.appSafetynet.model.dto.ChildAndHisHouseholdForUrl2;
import com.safetynet.appSafetynet.model.dto.ListOfChildrenAndTheHouseholdWithOneAddressUrl2;
import com.safetynet.appSafetynet.model.dto.ListOfPersonsCoveredByAFirestationUrl1;
import com.safetynet.appSafetynet.model.dto.PersonCoveredByAFirestationForUrl1;
import com.safetynet.appSafetynet.repository.FirestationRepository;
import com.safetynet.appSafetynet.repository.MakingModels;
import com.safetynet.appSafetynet.repository.MedicalrecordsRepository;
import com.safetynet.appSafetynet.repository.PersonRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

/*
    public HashMap<String, ArrayList> getMapOfPersonsServedByOneStation(String numberOfStation){
        numberOfAdults =0;
        numberOfChildren =0;
        HashMap<String, ArrayList> mapOfPersonsServedByOneStation = new HashMap<>();


        for (FirestationModel element : arrayListFirestations) {
            if (numberOfStation.equals(element.getStation())){
                String address = element.getAddress();
                for (PersonModel person : arrayListPersons) {
                    if (person.getAddress().equals(address)) {
                        ArrayList<String> dataPerOnePerson = new ArrayList<>();
                        String id = person.getFirstName() + " " + person.getLastName();
                        dataPerOnePerson.add(person.getAddress());
                        dataPerOnePerson.add(person.getPhone());
                        mapOfPersonsServedByOneStation.put(id, dataPerOnePerson);
                        if(person.getAge()>18) {
                            numberOfAdults++;
                        }
                        else{
                            numberOfChildren++;
                        }
                    }
                }
            }
        }


        //trouver les adresses desservies par une station

        System.out.println(mapOfPersonsServedByOneStation.size());//print size = 0
        return mapOfPersonsServedByOneStation;
    }
*/
    public ListOfChildrenAndTheHouseholdWithOneAddressUrl2 urlTwo(String address) {
        setUp();
        ListOfChildrenAndTheHouseholdWithOneAddressUrl2 listOfChildrenAndTheHouseholdWithOneAddressUrl2 = new ListOfChildrenAndTheHouseholdWithOneAddressUrl2();
        List<PersonModel> householdPersonModels = personRepository.getPeopleInSameHouseHold(address, listOfPersonModels);
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
        HashMap<String, ArrayList<String>> resultUrl3 = new HashMap<>();
        ArrayList<String> phoneNumbersCoveredByOneStation = new ArrayList<>();



        ArrayList <String> listOfAddressesServedByOneStation = firestationRepository.findAddressesServedByOneStation(numberOfStation, listOfFirestationModels);
        for(String address:listOfAddressesServedByOneStation){
            for(PersonModel person : listOfPersonModels.getListOfPersonModels() ){
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
        HashMap<String, Object> householdDataWithNameKey =  getHouseholdDataWithNameKey(address, arrayListPersons);

        resultUrl4.put("firestation number of "+ address,numberOfStation );
        resultUrl4.put ("persons living at "+ address, householdDataWithNameKey );


        return resultUrl4;
    }

    /*public List<PersonModel person> getHousehold(String address){

        ArrayList<PersonModel> houseHold = personRepository.getPeopleInSameHouseHold(address, listOfPersonModels);
        for (PersonModel item : arrayListHousehold){ //pour chaque personModel dans la liste du foyer
            HashMap<String, Object> dataForOnePerson = new HashMap<>();//crée une hashmap vide "dataforoneperson"

            dataForOnePerson.put("phone", item.getPhone());
            dataForOnePerson.put("age", item.getAge());
            dataForOnePerson.put("medications", item.getMedicalrecords().getMedications());
            dataForOnePerson.put("allergies", item.getMedicalrecords().getAllergies());


            householdDataWithNameKey.put(item.getFirstName()+" "+item.getLastName(), dataForOnePerson);


        }
        return householdDataWithNameKey;
    }
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
        List<String> numbersOfFirestations = Arrays.asList(stringNumbersOfFirestations.split(","));
        for (String numberOfFirestation : numbersOfFirestations){
            System.out.println("number = "+numberOfFirestation);
            HashMap<String, Object>householdDataWithAddressesKeys = new HashMap<>();
            ArrayList<String> addressesServedByOneStation = firestationRepository.findAddressesServedByOneStation(numberOfFirestation, arrayListFirestations);

            for (String address : addressesServedByOneStation){
                HashMap<String, Object> householdDataWithNameKeys = getHouseholdDataWithNameKey(address, arrayListPersons);
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
    public ArrayList<String> urlSeven(String city) {
        ArrayList<String> resultUrl7 = new ArrayList<>();

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

        for (PersonModel person : arrayListPersons){
            if(person.getCity().equals(city)){
                resultUrl7.add(person.getEmail());
            }

        }

        return resultUrl7;

    }*/
}





