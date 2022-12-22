package com.safetynet.appSafetynet.controller;

import com.safetynet.appSafetynet.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    /*
    @GetMapping("/persons")
    public Iterable<Person> getPersons() {
        return personService.getPersons();
    }*/

}
