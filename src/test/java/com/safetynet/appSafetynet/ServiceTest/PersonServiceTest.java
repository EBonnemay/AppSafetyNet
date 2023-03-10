package com.safetynet.appSafetynet.ServiceTest;

import com.jsoniter.any.Any;
import com.safetynet.appSafetynet.model.ListOfPersonModels;

import com.safetynet.appSafetynet.model.PersonModel;
import com.safetynet.appSafetynet.repository.MakingModels;
import com.safetynet.appSafetynet.repository.PersonRepository;
import com.safetynet.appSafetynet.service.PersonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonServiceTest {
    @Autowired
    PersonService personService;
    @Autowired
    PersonRepository personRepository;


    @Autowired
    ListOfPersonModels listOfPersonModels;

    @BeforeEach

    public void setUp()  {
        MakingModels makingModels = personRepository.getMakingModels();
        Any root = makingModels.modelMaker("classpath:data.json");

        listOfPersonModels = personRepository.fillInPersonModels(root);
    }

    @Test

    public void getPersonsTest() {
        ListOfPersonModels result = personService.getPersons();
        Assertions.assertNotEquals(0, result.getListOfPersonModels().size());

    }
    @Test
    public void updatePersonTest(){
        //String address = listOfFirestationModels.getListOfFirestationModels().get(5).getAddress();
        PersonModel updated = listOfPersonModels.getListOfPersonModels().get(3);
        updated.setEmail("rboyd@yahoo.fr");
        personService.updatePerson(updated);
        Assertions.assertEquals("rboyd@yahoo.fr", listOfPersonModels.getListOfPersonModels().get(3).getEmail());
    }
    @Test
    public void deletePersonTest() {
        PersonModel model = listOfPersonModels.getListOfPersonModels().get(3);
        String firstName = model.getFirstName();
        String lastName = model.getLastName();
        personService.deletePerson(firstName+" "+lastName);
        Assertions.assertFalse(listOfPersonModels.getListOfPersonModels().contains(model));
    }
    @Test
    public void addPersonTest(){
        PersonModel added = new PersonModel();
        added.setFirstName("Nawal");
        added.setLastName("Marwan");
        //listOfFirestationModels.getListOfFirestationModels().add(expected);
        personService.addPerson(added);
        Assertions.assertTrue(listOfPersonModels.getListOfPersonModels().contains(added));
    }

}
