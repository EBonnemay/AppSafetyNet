package com.safetynet.appSafetynet.repository;

import com.jsoniter.any.Any;
import com.safetynet.appSafetynet.model.ListOfMedicalrecordsModels;
import com.safetynet.appSafetynet.model.ListOfPersonModels;
import com.safetynet.appSafetynet.model.MedicalrecordsModel;
import com.safetynet.appSafetynet.model.PersonModel;
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
    ListOfPersonModels listOfPersonModels = new ListOfPersonModels();

    Any root;
    public PersonRepository() throws FileNotFoundException {
    }
    public ListOfPersonModels fillInPersonModels(Any deserializedFile){
        List<PersonModel> attributeList = new ArrayList<>();
        try {
            //deserializedFile = makingModels.modelMaker();
            ListOfMedicalrecordsModels listOfMedicalrecordsModels = medicalrecordsRepository.fillInMedicalrecordsModels(deserializedFile);
            Any json_persons = deserializedFile.get("persons");
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
                model.setListOfAllergies (listOfMedicalrecordsModels.getListOfMedicalrecordsModels().get(i).getAllergies());
                model.setMap0fMedications((listOfMedicalrecordsModels).getListOfMedicalrecordsModels().get(i).getMedications());
                model.setDateOfBirth(listOfMedicalrecordsModels.getListOfMedicalrecordsModels().get(i).getBirthdate());
                //String dateOfBirth = model.getMedicalrecords().getBirthdate();
                model.setAge(howOldIsThisPerson(model.getDateOfBirth()));


                //model.setMedicalrecords();

                attributeList.add(model);
            }
        } catch(Exception e){
            System.out.println("problems filling models");
        }
        listOfPersonModels.setListOfPersonModels(attributeList);
        return listOfPersonModels;
    }
    public ListOfPersonModels findAll() {
        //ListOfPersonModels listOfPersonModels = new ListOfPersonModels();
        /*if (listOfPersonModels==null) {
            if (root == null) {
                root = makingModels.modelMaker();
            }
        }*/
        listOfPersonModels =  fillInPersonModels(root);
        return listOfPersonModels;
    }
    public void addOnePerson(PersonModel person) {
        if (listOfPersonModels == null) {
            if (root == null) {
                root = makingModels.modelMaker();
            }
            listOfPersonModels = fillInPersonModels(root);
        }

        listOfPersonModels.getListOfPersonModels().add(person);

    }
    public void deleteOnePerson(String firstLastName){
        if (listOfPersonModels == null) {
            if (root == null) {
                root = makingModels.modelMaker();
            }
            listOfPersonModels = fillInPersonModels(root);
        }
        for(PersonModel person : listOfPersonModels.getListOfPersonModels()){
            if(firstLastName.equals(person.getFirstName()+" "+person.getLastName())){
                listOfPersonModels.getListOfPersonModels().remove(person);
            }
        }
    }
    public void updatePerson(String firstLastName, String field, String newContent ){
        if (listOfPersonModels ==null){
            if(root == null){
                root = makingModels.modelMaker();
            }
            listOfPersonModels = fillInPersonModels(root);
        }
        for (PersonModel element : listOfPersonModels.getListOfPersonModels()){
            if((element.getFirstName()+" "+ element.getLastName()).equals(firstLastName)){
                if(field.equals("address") ){
                    element.setAddress(newContent);
                }
                if(field.equals("city") ){
                    element.setCity(newContent);
                }
                if(field.equals("zip") ){
                    element.setZip(newContent);
                }
                if(field.equals("phone") ){
                    element.setPhone(newContent);
                }
                if(field.equals("email") ){
                    element.setEmail(newContent);
                }
            }
        }
        System.out.println("now person "+ firstLastName + " has "+field+ " "+ newContent );
    }
    public ArrayList<PersonModel> getPeopleInSameHouseHold(String address, ListOfPersonModels listOfPersonModels){
        ArrayList<PersonModel> peopleInSameHousehold = new ArrayList<>();

        for (PersonModel personModel : listOfPersonModels.getListOfPersonModels()) {//array is null
            //ArrayList<String> firstAndLastName = new ArrayList<>();
            if (address.equals(personModel.getAddress())) {
                peopleInSameHousehold.add(personModel);
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
