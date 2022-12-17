package com.safetynet.appSafetynet.controller;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.appSafetynet.model.Person;
import com.safetynet.appSafetynet.service.PersonService;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    /**
     * Read - Get all employees
     * @return - An Iterable object of Employee full filled
     */
    @GetMapping("/persons")
    public Iterable<Person> getPersons() {
        return personService.getPersons();
    }

}
