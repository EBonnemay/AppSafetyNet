package com.safetynet.appSafetynet.RepositoryTest;

import com.jsoniter.any.Any;
import com.safetynet.appSafetynet.model.ListOfPersonModels;
import com.safetynet.appSafetynet.model.ListOfPersonModelsForUrls;
import com.safetynet.appSafetynet.model.PersonModel;
import com.safetynet.appSafetynet.model.PersonModelForUrls;
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
    ListOfPersonModelsForUrls listOfPersonModelsForUrls;
    @Autowired
    MakingModels makingModels;

    @BeforeEach
    //désérialiser le fichier data
    public void setUp() {
        MakingModels makingModels = personRepository.getMakingModels();
        Any root = makingModels.modelMaker("classpath:data.json");

        listOfPersonModels = personRepository.fillInPersonModels(root);
        listOfPersonModelsForUrls = personRepository.fillInPersonModelsForUrls(root);
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
    public void fillInPersonModelsForUrlsTest() {
        listOfPersonModelsForUrls.getListOfPersonModelForUrls().clear();
        makingModels = personRepository.getMakingModels();
        Any root = makingModels.modelMaker("classpath:data.json");

        listOfPersonModelsForUrls = personRepository.fillInPersonModelsForUrls(root);
        Assertions.assertTrue(listOfPersonModelsForUrls.getListOfPersonModelForUrls().size()>0);

    }
    @Test
    public void findAllPersonsTest(){
        listOfPersonModels.getListOfPersonModels().clear();
        personRepository.findAll();


        Assertions.assertTrue(listOfPersonModels.getListOfPersonModels().size()>0);
    }
    @Test
    public void deletePersonTest(){
        PersonModel model = listOfPersonModels.getListOfPersonModels().get(5);
        String firstName = model.getFirstName();
        String lastName= model.getLastName();
        personRepository.deleteOnePerson(firstName+" "+lastName);
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
        personRepository.addOnePerson(model);
        Assertions.assertTrue(listOfPersonModels.getListOfPersonModels().contains(model));
    }
    @Test
    public void addAlreadyExistingPersonTest(){
        PersonModel model = listOfPersonModels.getListOfPersonModels().get(4);
        Assertions.assertThrows(RuntimeException.class, () ->personRepository.addOnePerson(model));
    }
    @Test
    public void updatePersonTest(){
        PersonModel updated = listOfPersonModels.getListOfPersonModels().get(14);
        updated.setAddress("2 chemin des platanes");
        updated.setCity("Paris");

        personRepository.updatePerson(updated);
        String city = "";
        String address = "";
        for(PersonModel person : listOfPersonModels.getListOfPersonModels()){
            if(person.getFirstName().equals("Reginold")&&person.getLastName().equals("Walker")){
                city = person.getCity();
                address = person.getAddress();
            }

        }
        Assertions.assertEquals("Paris", city);
        Assertions.assertEquals("2 chemin des platanes", address);
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
        ArrayList<PersonModelForUrls> actual = personRepository.getPeopleInSameHouseHold("892 Downing Ct", listOfPersonModelsForUrls);
        ArrayList<PersonModelForUrls> expected = new ArrayList<>();
        for(PersonModelForUrls person : listOfPersonModelsForUrls.getListOfPersonModelForUrls()){
            if(person.getLastName().equals("Zemicks")){
                expected.add(person);
            }
        }
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void howOldIsThisPerson2Test(){
        String dateOfBirth = "10/31/2017";
        Assertions.assertEquals(5, personRepository.howOldIsThisPerson(dateOfBirth));

    }
}
