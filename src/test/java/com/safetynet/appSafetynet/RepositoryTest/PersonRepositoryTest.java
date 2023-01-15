package com.safetynet.appSafetynet.RepositoryTest;

import com.jsoniter.any.Any;
import com.jsoniter.spi.JsonException;
import com.safetynet.appSafetynet.model.ListOfPersonModels;

import com.safetynet.appSafetynet.model.PersonModel;

import com.safetynet.appSafetynet.repository.MakingModels;
import com.safetynet.appSafetynet.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonRepositoryTest {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    ListOfPersonModels listOfPersonModels;

    @Autowired
    MakingModels makingModels;

    @BeforeEach
    //désérialiser le fichier data
    public void setUp() {
        MakingModels makingModels = personRepository.getMakingModels();
        Any root = makingModels.modelMaker("classpath:data.json");

        listOfPersonModels = personRepository.fillInPersonModels(root);
    }

   @Test
   public void fillInPersonModelsTest() {
       listOfPersonModels.getListOfPersonModels().clear();
       makingModels = personRepository.getMakingModels();
       Any root = makingModels.modelMaker("classpath:data.json");

       listOfPersonModels = personRepository.fillInPersonModels(root);
       Assertions.assertTrue(listOfPersonModels.getListOfPersonModels().size()>0);

   }
    @Test
    public void fillInPersonModelTestIfNoStringPersonFound (){
        listOfPersonModels.getListOfPersonModels().clear();
        MakingModels makingModels = personRepository.getMakingModels();
        Any root = makingModels.modelMaker("classpath:dataWithWrongKeysForTest.json");

        Assertions.assertThrows(JsonException.class, ()-> personRepository.fillInPersonModels(root));

    }
    @Test
    public void findAllPersonsTest(){
        listOfPersonModels.getListOfPersonModels().clear();
        listOfPersonModels = personRepository.findAll();


        Assertions.assertTrue(listOfPersonModels.getListOfPersonModels().size()>0);
    }
    @Test
    public void deletePersonTest(){
        PersonModel model = listOfPersonModels.getListOfPersonModels().get(5);
        String firstName = model.getFirstName();
        String lastName= model.getLastName();
        listOfPersonModels = personRepository.deleteOnePerson(firstName+" "+lastName);
        Assertions.assertFalse(listOfPersonModels.getListOfPersonModels().contains(model));
    }
    @Test
    public void deleteNonExistentPerson(){

        Assertions.assertThrows(RuntimeException.class,()-> personRepository.deleteOnePerson("Julien Sorel"));


    }

    @Test
    public void addPersonTest() {
        PersonModel model = new PersonModel();
        model.setFirstName("Julien");
        model.setLastName("Sorel");
        model.setEmail("j@sorel.net");
        listOfPersonModels = personRepository.addOnePerson(model);
        Assertions.assertTrue(listOfPersonModels.getListOfPersonModels().contains(model));
    }
    @Test
    public void addAlreadyExistingPersonTest(){
        PersonModel model = listOfPersonModels.getListOfPersonModels().get(4);
        Assertions.assertThrows(RuntimeException.class, () ->personRepository.addOnePerson(model));
    }
    @Test
    public void updatePersonTest(){
        PersonModel toUpdate = listOfPersonModels.getListOfPersonModels().get(14);
        toUpdate.setAddress("2 chemin des platanes");
        toUpdate.setCity("Paris");

        PersonModel updated = personRepository.updatePerson(toUpdate);

        Assertions.assertEquals(updated, listOfPersonModels.getListOfPersonModels().get(14));
        Assertions.assertEquals("Paris", listOfPersonModels.getListOfPersonModels().get(14).getCity());
        Assertions.assertEquals("2 chemin des platanes", listOfPersonModels.getListOfPersonModels().get(14).getAddress());
    }
    @Test
    public void updateNonExistentPersonTest(){
        PersonModel unexistent = new PersonModel();
        unexistent.setFirstName("Georges");
        unexistent.setLastName("Kaplan");
        unexistent.setAddress("2 chemin des platanes");
        unexistent.setCity("Paris");
        Assertions.assertThrows(RuntimeException.class, () -> personRepository.updatePerson(unexistent));
    }
    @Test
    public void getPeopleInSameHouseholdTest(){
        ArrayList<PersonModel> actual = personRepository.getPeopleInSameHouseHold("892 Downing Ct", listOfPersonModels);
        ArrayList<PersonModel> expected = new ArrayList<>();
        for(PersonModel person : listOfPersonModels.getListOfPersonModels()){
            if(person.getLastName().equals("Zemicks")){
                expected.add(person);
            }
        }
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void howOldIsThisPersonTest(){
        String dateOfBirth = "10/31/2017";
        Assertions.assertEquals(5, personRepository.howOldIsThisPerson(dateOfBirth));

    }
}
