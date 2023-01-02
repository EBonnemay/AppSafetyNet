package com.safetynet.appSafetynet.service;

import com.safetynet.appSafetynet.model.ListOfPersonModels;
import com.safetynet.appSafetynet.model.PersonModel;
import com.safetynet.appSafetynet.repository.PersonRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public ListOfPersonModels getPersons(){
        return personRepository.findAll();
    }
    public void updateP(String firstLastName, String field, String newContent ){
        personRepository.updatePerson(firstLastName, field, newContent);
    }
    public void deletePerson(String firstLastName){
        personRepository.deleteOnePerson(firstLastName);
    }
    public void addPerson(PersonModel personModel){
        personRepository.addOnePerson(personModel);
    }



}
