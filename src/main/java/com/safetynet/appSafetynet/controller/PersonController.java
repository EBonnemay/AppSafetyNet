package com.safetynet.appSafetynet.controller;

import com.safetynet.appSafetynet.model.ListOfPersonModels;
import com.safetynet.appSafetynet.model.PersonModel;
import com.safetynet.appSafetynet.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/person")
    public ListOfPersonModels getPersons(){
        return personService.getPersons();
    }

    @PutMapping("/person")
    public void updatePerson(@RequestParam String firstLastName, @RequestParam String field, @RequestParam String newContent){
        personService.updatePerson(firstLastName, field, newContent);
    }
    @PostMapping("/person")
    public void addPerson(@RequestBody PersonModel personModel){
        personService.addPerson(personModel);

    }
    @DeleteMapping("/person")
    public void deletePerson(@RequestParam String firstLastName){
        personService.deletePerson(firstLastName);
    }

}
