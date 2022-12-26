package com.safetynet.appSafetynet.repository;

import com.jsoniter.any.Any;
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
    @Autowired
    MakingModels makingModels;
    ArrayList<PersonModel> arrayListPersons = new ArrayList<>();
    public PersonRepository() throws FileNotFoundException {
    }
    public void makePersonModels(){
        try {
            Any deserializedFile = makingModels.modelMaker();
            Any json_persons = deserializedFile.get("persons");
            List<Any> list = json_persons.asList();


            for (int i = 0; i < list.size(); i++) {
                PersonModel model = new PersonModel();
                model.setFirstName(list.get(i).get("firstName").toString());
                model.setLastName(list.get(i).get("lastName").toString());
                model.setAddress(list.get(i).get("address").toString());
                model.setCity(list.get(i).get("city").toString());
                model.setZip(list.get(i).get("zip").toString());
                model.setPhone(list.get(i).get("phone").toString());
                model.setEmail(list.get(i).get("email").toString());

                arrayListPersons.add(model);
            }
        } catch(Exception e){
            System.out.println("problems filling models");
        }
    }
    public Iterable<PersonModel> findAll() {
        //arrayListPersons.clear();
        if (arrayListPersons.isEmpty()){
            makePersonModels();
        }
        return arrayListPersons;
    }
    public void addOnePerson(){


    }
    public void deleteOnePerson(String firstLastName){

    }
    public void updatePerson(String firstLastName, String field, String newContent ){
        if (arrayListPersons.isEmpty()){
            makePersonModels();
        }
        for (PersonModel element : arrayListPersons){
            if((element.getFirstName()+element.getLastName()).equals(firstLastName)){
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
}
