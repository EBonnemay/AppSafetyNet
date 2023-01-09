package com.safetynet.appSafetynet.RepositoryTest;

import com.jsoniter.any.Any;
import com.safetynet.appSafetynet.model.ListOfPersonModels;
import com.safetynet.appSafetynet.model.ListOfPersonModelsForUrls;
import com.safetynet.appSafetynet.model.PersonModel;
import com.safetynet.appSafetynet.model.PersonModelForUrls;
import com.safetynet.appSafetynet.repository.MakingModels;
import com.safetynet.appSafetynet.repository.PersonRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
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
    public void setUp() throws FileNotFoundException {
        MakingModels makingModels = personRepository.getMakingModels();
        Any root = makingModels.modelMaker();

        listOfPersonModels = personRepository.fillInPersonModels(root);
        listOfPersonModelsForUrls = personRepository.fillInPersonModelsForUrls(root);
    }


    @Tag("")
    @DisplayName("")

    @Test
    public void setUpPersonModelsTest(){
//ARRANGE

        // vérifier que personModels n'est plus vide après passage de la méthode
//ACT
        //personRepository.makePersonModels(Any deseralizedFile)
// ASSERT  l'attribut arrayList n'est pas vide
    }


   @Test
   public void fillInPersonModelsTest() {
       listOfPersonModels.getListOfPersonModels().clear();
       makingModels = personRepository.getMakingModels();
       Any root = makingModels.modelMaker();

       listOfPersonModels = personRepository.fillInPersonModels(root);
       Assertions.assertTrue(listOfPersonModels.getListOfPersonModels().size()>0);

   }
    @Test
    public void fillInPersonModelsForUrlsTest() {
        listOfPersonModelsForUrls.getListOfPersonModelForUrls().clear();
        makingModels = personRepository.getMakingModels();
        Any root = makingModels.modelMaker();

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
//ARRANGE
        //ArrayList persons remplie en attribut de classe
//ACT
//ASSERT
    }
    @Test
    public void addPersonTest() {
//ARRANGE
        //FirestationModel added = new FirestationModel();
        //added.setAddress("10 downing street");
       // added.setStation("5");
        //listOfFirestationModels.getListOfFirestationModels().add(expected);
       // firestationRepository.addOneAddressStationMapping("10 downing street", "5");
//ACT

        //ArrayList persons remplie en attribut de classe
//ACT
//ASSERT

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
//ARRANGE
        String dateOfBirth = "10/31/2017";
        int Expected  = 5;
        Assertions.assertEquals(5, personRepository.howOldIsThisPerson(dateOfBirth));
        //ACT //ASSERT


    }
}
