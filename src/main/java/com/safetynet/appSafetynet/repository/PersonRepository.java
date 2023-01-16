package com.safetynet.appSafetynet.repository;

import com.jsoniter.any.Any;
import com.jsoniter.spi.JsonException;
import com.safetynet.appSafetynet.model.*;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@Repository
public class PersonRepository implements IPersonRepository{
    ArrayList<MedicalrecordsModel> arrayListMedicalrecords = new ArrayList<>();
    @Autowired
    MakingModels makingModels;

    @Autowired
    IMedicalrecordsRepository medicalrecordsRepository;

    @Autowired
    ListOfPersonModels listOfPersonModels;
    @Autowired
    ListOfMedicalrecordsModels listOfMedicalrecordsModels;

    Any root;
    static final Logger logger = LogManager.getLogger();

    public PersonRepository()  {
    }

    @Override
    public ListOfPersonModels fillInPersonModels(Any deserializedFile) {
        List<PersonModel> attributeList = new ArrayList<>();
        Any json_persons;

        try {
            json_persons = deserializedFile.get("persons");


            List<Any> listPersons = json_persons.asList();
            //listOfMedicalrecordsModels = medicalrecordsRepository.findAll();


            for (int i = 0; i < listPersons.size(); i++) {
                PersonModel model = new PersonModel();
                model.setFirstName(listPersons.get(i).get("firstName").toString());
                model.setLastName(listPersons.get(i).get("lastName").toString());
                model.setAddress(listPersons.get(i).get("address").toString());
                model.setCity(listPersons.get(i).get("city").toString());
                model.setZip(listPersons.get(i).get("zip").toString());
                model.setPhone(listPersons.get(i).get("phone").toString());
                model.setEmail(listPersons.get(i).get("email").toString());

                for(MedicalrecordsModel medicalFile : listOfMedicalrecordsModels.getListOfMedicalrecordsModels()){
                    if((medicalFile.getFirstName()+" "+medicalFile.getLastName()).equals((model.getFirstName()+" "+model.getLastName()))){
                        model.setMedicalrecordsModel(medicalFile);
                    }
                }
                attributeList.add(model);
            }

        } catch(JsonException e) {
            logger.error("the key 'persons' was not found in deserialized file");
            throw new JsonException("key not found", e);
        }
            listOfPersonModels.setListOfPersonModels(attributeList);
            return listOfPersonModels;

    }
   @Override
    public void setUpListOfPersonModels() {
       //

       if (listOfPersonModels.getListOfPersonModels().size() == 0) {
            if (root == null) {
                root = makingModels.modelMaker("classpath:data.json");
            }
           listOfMedicalrecordsModels = medicalrecordsRepository.findAll();
            if (listOfMedicalrecordsModels.getListOfMedicalrecordsModels().isEmpty()){
                listOfMedicalrecordsModels = medicalrecordsRepository.fillInMedicalrecordsModels(root);
            }

            listOfPersonModels = fillInPersonModels(root);

        }
    }

    @Override
    public ListOfPersonModels findAll() {
        listOfMedicalrecordsModels = medicalrecordsRepository.findAll();
        setUpListOfPersonModels();
        for(int i = 0;i<listOfPersonModels.getListOfPersonModels().size();i++){
            String firstLastNamePerson = listOfPersonModels.getListOfPersonModels().get(i).getFirstName()+listOfPersonModels.getListOfPersonModels().get(i).getLastName();
            for(MedicalrecordsModel medFile : listOfMedicalrecordsModels.getListOfMedicalrecordsModels()){
                if(firstLastNamePerson.equals((medFile.getFirstName()+medFile.getLastName()))){
                    listOfPersonModels.getListOfPersonModels().get(i).setMedicalrecordsModel(medFile);
                }
            }
        }
        //tentative dactualiser persons à partir d'un mr actualisé"
        return listOfPersonModels;
    }

    @Override
    public ListOfPersonModels addOnePerson(PersonModel person) {
        setUpListOfPersonModels();
        if(!listOfPersonModels.getListOfPersonModels().contains(person)){
            listOfPersonModels.getListOfPersonModels().add(person);
        }
        else{
            logger.error("the person to add is already in the data. No adding");
            throw new RuntimeException("no adding");
        }
    return listOfPersonModels;
    }
    @Override
    public ListOfPersonModels deleteOnePerson(String firstLastName){
        setUpListOfPersonModels();
        int match = 0;
        for(int i = 0; i<listOfPersonModels.getListOfPersonModels().size();i++){
            if((listOfPersonModels.getListOfPersonModels().get(i).getFirstName()+" "+
                    listOfPersonModels.getListOfPersonModels().get(i).getLastName()).equals(firstLastName)) {
                match = 1;
                listOfPersonModels.getListOfPersonModels().remove(listOfPersonModels.getListOfPersonModels().get(i));

            }

        }
        if (match == 0) {
            logger.error("the person to delete is not in the data. No deleting");
            throw new RuntimeException("no deleting");
        }
    return listOfPersonModels;
    }
    @Override
    public PersonModel updatePerson(PersonModel updatedPerson ) {
        setUpListOfPersonModels();
        int match = 0;
        PersonModel result = new PersonModel();
        for (int i = 0; i < listOfPersonModels.getListOfPersonModels().size(); i++) {
            String firstName = listOfPersonModels.getListOfPersonModels().get(i).getFirstName();
            String lastName = listOfPersonModels.getListOfPersonModels().get(i).getLastName();
            if (firstName.equals(updatedPerson.getFirstName()) && lastName.equals(updatedPerson.getLastName())) {
                match = 1;
                listOfPersonModels.getListOfPersonModels().set(i, updatedPerson);
                result = listOfPersonModels.getListOfPersonModels().get(i);
                //ici update medicalRecords
                MedicalrecordsModel misterUpdatedsMedicalFile = updatedPerson.getMedicalrecordsModel();
                for(int j=0;j<listOfMedicalrecordsModels.getListOfMedicalrecordsModels().size();j++){

                    MedicalrecordsModel model = listOfMedicalrecordsModels.getListOfMedicalrecordsModels().get(j);
                    if(model.getFirstName().equals(firstName)&&model.getLastName().equals(lastName)){
                        listOfMedicalrecordsModels.getListOfMedicalrecordsModels().set(i,misterUpdatedsMedicalFile);
                    }
                }//ici update medicalrecords

            }
        }
        if(match==0){
            logger.error("the person to update is not in the data. No updating");
            throw new RuntimeException("no updating");
        }
        return result;
    }
    @Override
    public ArrayList<PersonModel> getPeopleInSameHouseHold(String address, ListOfPersonModels listOfPersonModels){
        ArrayList<PersonModel> peopleInSameHousehold = new ArrayList<>();
        int match = 0;
        for (PersonModel personModel : listOfPersonModels.getListOfPersonModels()) {//array is null
            //ArrayList<String> firstAndLastName = new ArrayList<>();
            if (address.equals(personModel.getAddress())) {
                match = 1;
                peopleInSameHousehold.add(personModel);
            }
        }
        if( match == 0){
            logger.error("address required not found in the data.");
            throw new RuntimeException("no adding");
        }
        return peopleInSameHousehold;
    }
    @Override
    public int howOldIsThisPerson(String stringDateOfBirth){
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate dateOfBirth = LocalDate.parse(stringDateOfBirth, formatter);
        return Period.between(dateOfBirth, today).getYears();
    }

}
