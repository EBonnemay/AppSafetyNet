package com.safetynet.appSafetynet.repository;

import com.jsoniter.any.Any;
import com.jsoniter.spi.JsonException;
import com.safetynet.appSafetynet.model.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@Repository
public class PersonRepository{
    ArrayList<MedicalrecordsModel> arrayListMedicalrecords = new ArrayList<>();
    @Autowired
    MakingModels makingModels;

    @Autowired
    MedicalrecordsRepository medicalrecordsRepository;
    //ArrayList<PersonModel> arrayListPersons = new ArrayList<>();
    @Autowired
    ListOfPersonModelsForUrls listOfPersonModelsForUrls;
    @Autowired
    ListOfPersonModels listOfPersonModels;


    Any root;
    public PersonRepository() throws FileNotFoundException {
    }
    public ListOfPersonModelsForUrls fillInPersonModelsForUrls(Any deserializedFile) {
        List<PersonModelForUrls> attributeList = new ArrayList<>();
        try {
            //deserializedFile = makingModels.modelMaker();
            ListOfMedicalrecordsModels listOfMedicalrecordsModels = medicalrecordsRepository.fillInMedicalrecordsModels(deserializedFile);
            Any json_persons = deserializedFile.get("persons");
            //Any json_medicalrecords = deserializedFile.get("medicalrecords");

            List<Any> listPersons = json_persons.asList();


            for (int i = 0; i < listPersons.size(); i++) {
                PersonModelForUrls model = new PersonModelForUrls();
                model.setFirstName(listPersons.get(i).get("firstName").toString());
                model.setLastName(listPersons.get(i).get("lastName").toString());
                model.setAddress(listPersons.get(i).get("address").toString());
                model.setCity(listPersons.get(i).get("city").toString());
                model.setZip(listPersons.get(i).get("zip").toString());
                model.setPhone(listPersons.get(i).get("phone").toString());
                model.setEmail(listPersons.get(i).get("email").toString());
                model.setListOfAllergies(listOfMedicalrecordsModels.getListOfMedicalrecordsModels().get(i).getAllergies());
                model.setMap0fMedications((listOfMedicalrecordsModels).getListOfMedicalrecordsModels().get(i).getMedications());
                model.setDateOfBirth(listOfMedicalrecordsModels.getListOfMedicalrecordsModels().get(i).getBirthdate());
                //String dateOfBirth = model.getMedicalrecords().getBirthdate();
                model.setAge(howOldIsThisPerson(model.getDateOfBirth()));


                //model.setMedicalrecords();

                attributeList.add(model);
            }
        }catch(JsonException e){

            System.out.println("person key not found in file");
        }catch(Exception e){
            System.out.println("problems filling models");
            //throw new RuntimeException("tentative arrêt programme",e);
        }
        listOfPersonModelsForUrls.setListOfPersonModelForUrls(attributeList);
        return listOfPersonModelsForUrls;
    }
    public ListOfPersonModels fillInPersonModels(Any deserializedFile) {
        List<PersonModel> attributeList = new ArrayList<>();
        Any json_persons;
        try {
            //deserializedFile = makingModels.modelMaker();
            //ListOfMedicalrecordsModels listOfMedicalrecordsModels = medicalrecordsRepository.fillInMedicalrecordsModels(deserializedFile);
            json_persons = deserializedFile.get("persons");
            //Any json_medicalrecords = deserializedFile.get("medicalrecords");

            List<Any> listPersons = json_persons.asList();


            for (int i = 0; i < listPersons.size(); i++) {
                PersonModel model = new PersonModel();
                model.setFirstName(listPersons.get(i).get("firstName").toString());
                model.setLastName(listPersons.get(i).get("lastName").toString());
                model.setAddress(listPersons.get(i).get("address").toString());
                model.setCity(listPersons.get(i).get("city").toString());
                model.setZip(listPersons.get(i).get("zip").toString());
                model.setPhone(listPersons.get(i).get("phone").toString());
                model.setEmail(listPersons.get(i).get("email").toString());



                //model.setMedicalrecords();

                attributeList.add(model);
            }
            listOfPersonModels.setListOfPersonModels((attributeList));
        }catch(JsonException e){

            System.out.println("person key not found in file");
        }catch(Exception e){
            System.out.println("problems filling models");
            //throw new RuntimeException("tentative arrêt programme",e);
        }
        listOfPersonModels.setListOfPersonModels(attributeList);
        return listOfPersonModels;
    }

    public void setUpListOfPersonModelsForUrls() {
        if (listOfPersonModelsForUrls.getListOfPersonModelForUrls().size() == 0) {
            if (root == null) {
                root = makingModels.modelMaker();
            }
            listOfPersonModelsForUrls = fillInPersonModelsForUrls(root);
            System.out.println("listOfPersonModels for Urls filled in");
        }
    }
    public void setUpListOfPersonModels(){
        if (listOfPersonModels.getListOfPersonModels().size()==0) {
            if (root == null) {
                root = makingModels.modelMaker();
            }
            listOfPersonModels = fillInPersonModels(root);
            System.out.println("listOfPersonModels filled in");
        }
    }
    public ListOfPersonModels findAll() {
        setUpListOfPersonModels();
        return listOfPersonModels;
    }
    public void addOnePerson(PersonModel person) {
        setUpListOfPersonModels();
        listOfPersonModels.getListOfPersonModels().add(person);

    }
    public void deleteOnePerson(String firstLastName){
        setUpListOfPersonModels();
        PersonModel personToDelete = new PersonModel();
        for(PersonModel person : listOfPersonModels.getListOfPersonModels()) {
            if (firstLastName.equals(person.getFirstName() + " " + person.getLastName())) {
                personToDelete = person;
            }
        }
        listOfPersonModels.getListOfPersonModels().remove(personToDelete);

    }
    public void updatePerson(PersonModel updatedPerson ) {
        setUpListOfPersonModels();
        for (int i = 0; i < listOfPersonModels.getListOfPersonModels().size(); i++) {
            if (listOfPersonModels.getListOfPersonModels().get(i).getFirstName().equals(updatedPerson.getFirstName()) && listOfPersonModels.getListOfPersonModels().get(i).getLastName().equals(updatedPerson.getLastName())) {
                listOfPersonModels.getListOfPersonModels().set(i, updatedPerson);
                //listOfPersonModels.getListOfPersonModels().remove(i);
                //listOfPersonModels.getListOfPersonModels().add(updatedPerson);
            }
        }


    }
    public ArrayList<PersonModelForUrls> getPeopleInSameHouseHold(String address, ListOfPersonModelsForUrls listOfPersonModelsForUrls){
        ArrayList<PersonModelForUrls> peopleInSameHousehold = new ArrayList<>();

        for (PersonModelForUrls personModelForUrls : listOfPersonModelsForUrls.getListOfPersonModelForUrls()) {//array is null
            //ArrayList<String> firstAndLastName = new ArrayList<>();
            if (address.equals(personModelForUrls.getAddress())) {
                peopleInSameHousehold.add(personModelForUrls);
            }

        }
        return peopleInSameHousehold;
    }
    public int howOldIsThisPerson(String stringDateOfBirth){
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        System.out.println("formatter ok");

        LocalDate dateOfBirth = LocalDate.parse(stringDateOfBirth, formatter);
        int age = Period.between(dateOfBirth, today).getYears();
        System.out.println("age"+ age);
        return age;
    }

}
