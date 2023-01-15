package com.safetynet.appSafetynet.controller;

import com.safetynet.appSafetynet.model.ListOfPersonModels;
import com.safetynet.appSafetynet.model.PersonModel;
import com.safetynet.appSafetynet.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;
    static final Logger logger = LogManager.getLogger();


    @GetMapping("/person")
    public ListOfPersonModels getPersons(){
        return personService.getPersons();
    }

    @PutMapping("/person")
    public PersonModel updatePerson(@RequestBody PersonModel person){
        PersonModel result= new PersonModel();
        try{
            result = personService.updatePerson(person);
            logger.info("person updated successfully");
            return result;
        }catch(RuntimeException e){
            logger.error("person not found");
            return result;
        }
    }
    @PostMapping("/person")
    public ListOfPersonModels addPerson(@RequestBody PersonModel personModel){
        ListOfPersonModels result = new ListOfPersonModels();
        try {
            result = personService.addPerson(personModel);
            logger.info("person added successfully");
            return result;
        } catch (RuntimeException e) {
            logger.error("item not found");
            return result;
        }


    }
    @DeleteMapping("/person")
    public ListOfPersonModels deletePerson(@RequestParam String firstLastName){
        ListOfPersonModels result = new ListOfPersonModels();
        try {
            result = personService.deletePerson(firstLastName);
            logger.info("person deleted successfully");
            return result;
        } catch (RuntimeException e) {
            logger.error("item not found");
            return result;
        }

    }

}
