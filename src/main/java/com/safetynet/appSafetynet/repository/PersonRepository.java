package com.safetynet.appSafetynet.repository;

import com.jsoniter.any.Any;
import com.safetynet.appSafetynet.model.MedicalrecordsModel;
import com.safetynet.appSafetynet.model.PersonModel;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
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
    ArrayList<PersonModel> arrayListPersons = new ArrayList<>();


    Any root;
    public PersonRepository() throws FileNotFoundException {
    }
    public void makePersonModels(Any deserializedFile){
        try {
            //deserializedFile = makingModels.modelMaker();
            medicalrecordsRepository.makeMedicalrecordsModels(deserializedFile);
            this.arrayListMedicalrecords = medicalrecordsRepository.getArrayListMedicalrecords();
            Any json_persons = deserializedFile.get("persons");
            Any json_medicalrecords = deserializedFile.get("medicalrecords");

            List<Any> listPersons = json_persons.asList();
            //List<Any> listMedicalrecords = json_medicalrecords.asList();


            for (int i = 0; i < listPersons.size(); i++) {
                PersonModel model = new PersonModel();
                model.setFirstName(listPersons.get(i).get("firstName").toString());
                model.setLastName(listPersons.get(i).get("lastName").toString());
                model.setAddress(listPersons.get(i).get("address").toString());
                model.setCity(listPersons.get(i).get("city").toString());
                model.setZip(listPersons.get(i).get("zip").toString());
                model.setPhone(listPersons.get(i).get("phone").toString());
                model.setEmail(listPersons.get(i).get("email").toString());
                model.setMedicalrecords(arrayListMedicalrecords.get(i));


                //model.setMedicalrecords();

                arrayListPersons.add(model);
            }
        } catch(Exception e){
            System.out.println("problems filling models");
        }
    }
    public Iterable<PersonModel> findAll() {
        //arrayListPersons.clear();
        if (arrayListPersons.isEmpty()){
            if(root == null){
                root = makingModels.modelMaker();
            }
            makePersonModels(root);
        }
        return arrayListPersons;
    }
    public void addOnePerson(){


    }
    public void deleteOnePerson(String firstLastName){

    }
    public void updatePerson(String firstLastName, String field, String newContent ){
        if (arrayListPersons.isEmpty()){
            if(root == null){
                root = makingModels.modelMaker();
            }
            makePersonModels(root);
        }
        for (PersonModel element : arrayListPersons){
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
    public ArrayList<PersonModel> getPeopleInSameHouseHold(String address, ArrayList<PersonModel>array){
        ArrayList<PersonModel> peopleInSameHousehold = new ArrayList<>();

        for (PersonModel personModel : array) {//array is null
            //ArrayList<String> firstAndLastName = new ArrayList<>();
            if (address.equals(personModel.getAddress())) {
                peopleInSameHousehold.add(personModel);
            }

        }
        return peopleInSameHousehold;
    }

}
