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
    public void updatePerson(@RequestBody PersonModel person){
        personService.updatePerson(person);
        logger.info("Person updated successfully");
    }
    @PostMapping("/person")
    public void addPerson(@RequestBody PersonModel personModel){
        personService.addPerson(personModel);
        logger.info("Person added successfully");

    }
    @DeleteMapping("/person")
    public void deletePerson(@RequestParam String firstLastName){
        personService.deletePerson(firstLastName);
        logger.info("Person deleted successfully");
    }

}
