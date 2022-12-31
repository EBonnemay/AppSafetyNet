package com.safetynet.appSafetynet.controller;

import com.safetynet.appSafetynet.model.ListOfPersonModels;
import com.safetynet.appSafetynet.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/person")
    public ListOfPersonModels getPersons(){
        return personService.getPersons();
    }
    @PutMapping("/person")
    public  void updatePerson(String firstLastName, String field, String newContent){
        personService.updateP(firstLastName, field, newContent);
    }
}
