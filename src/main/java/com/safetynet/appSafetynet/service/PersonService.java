package com.safetynet.appSafetynet.service;

import com.safetynet.appSafetynet.model.ListOfPersonModels;
import com.safetynet.appSafetynet.model.PersonModel;
import com.safetynet.appSafetynet.repository.IPersonRepository;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class PersonService {
    @Autowired
    private IPersonRepository personRepository;
    static final Logger logger = LogManager.getLogger();

    public ListOfPersonModels getPersons(){
        return personRepository.findAll();
    }
    public PersonModel updatePerson(PersonModel updated ){
        return personRepository.updatePerson(updated);

    }
    public ListOfPersonModels deletePerson(String firstLastName){
        return  personRepository.deleteOnePerson(firstLastName);

    }
    public ListOfPersonModels addPerson(PersonModel personModel){
        return personRepository.addOnePerson(personModel);

    }



}
