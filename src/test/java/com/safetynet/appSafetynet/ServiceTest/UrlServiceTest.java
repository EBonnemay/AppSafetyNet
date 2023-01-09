package com.safetynet.appSafetynet.ServiceTest;

import com.jsoniter.any.Any;
import com.safetynet.appSafetynet.model.ListOfFirestationModels;
import com.safetynet.appSafetynet.model.ListOfMedicalrecordsModels;
import com.safetynet.appSafetynet.model.ListOfPersonModels;
import com.safetynet.appSafetynet.model.ListOfPersonModelsForUrls;
import com.safetynet.appSafetynet.model.dto.*;
import com.safetynet.appSafetynet.repository.FirestationRepository;
import com.safetynet.appSafetynet.repository.MakingModels;
import com.safetynet.appSafetynet.repository.MedicalrecordsRepository;
import com.safetynet.appSafetynet.repository.PersonRepository;
import com.safetynet.appSafetynet.service.FirestationService;
import com.safetynet.appSafetynet.service.MedicalrecordsService;
import com.safetynet.appSafetynet.service.PersonService;
import com.safetynet.appSafetynet.service.UrlService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UrlServiceTest {
    @Autowired
    UrlService urlService;
    @Autowired
    FirestationRepository firestationRepository;
    @Autowired
    MedicalrecordsRepository medicalrecordsRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    FirestationService firestationService;
    @Autowired
    MedicalrecordsService medicalrecordsService;
    @Autowired
    PersonService personService;
    @Autowired
    ListOfMedicalrecordsModels listOfMedicalrecordsModels;
    @Autowired
    ListOfFirestationModels listOfFirestationModels;
    @Autowired
    ListOfPersonModelsForUrls listOfPersonModelsForUrls;
   @Autowired
   ListOfPersonModels listOfPersonModels;




    @BeforeAll

    //désérialiser le fichier data
    public void setUp() throws FileNotFoundException {
        MakingModels makingModels = firestationRepository.getMakingModels();
        Any root = makingModels.modelMaker();

        listOfFirestationModels = firestationRepository.fillInFirestationModels(root);
        listOfMedicalrecordsModels = medicalrecordsRepository.fillInMedicalrecordsModels(root);
        listOfPersonModelsForUrls = personRepository.fillInPersonModelsForUrls(root);
        listOfPersonModels = personRepository.fillInPersonModels(root);
    }






    @Test

    public void urlOneTest(){

        ListOfPersonsCoveredByAFirestationUrl1 result= urlService.urlOne("2");
        Assertions.assertEquals(5, result.getListOfPersonsCoveredByFirestation().size());
        Assertions.assertEquals(1, result.getNumberOfChildren());
        Assertions.assertEquals(4, result.getNumberOfAdults());
        //ArrayList persons remplie en attribut de classe
        //étant donnée une arrayList remplie de personModels, est ce que arrayList retourné par la méthode = arraylist attribut de classe?
    }
    @Test
    public void urlTwoTest(){
        ListOfChildrenAndTheHouseholdWithOneAddressUrl2 result = urlService.urlTwo("1509 Culver St");
        List<ChildAndHisHouseholdForUrl2> resultList = result.getListOfChidrenAndHouseholdWithOneAddress();
        ChildAndHisHouseholdForUrl2 child1AndHousehold = resultList.get(0);
        ChildAndHisHouseholdForUrl2 child2AndHousehold = resultList.get(1);
        String firstNameChild1 = child1AndHousehold.getFirstNameOfChild();
        String firstNameChild2 = child2AndHousehold.getFirstNameOfChild();
        String lastNameChild1 = child1AndHousehold.getLastNameOfChild();
        String lastNameChild2 = child2AndHousehold.getLastNameOfChild();
        int ageChild1 = child1AndHousehold.getAge();
        int ageChild2 = child2AndHousehold.getAge();
        List<String> listOfHouseholdChild1 = child1AndHousehold.getListOfOtherMembers();
        List<String> listOfHouseholdChild2 = child2AndHousehold.getListOfOtherMembers();

        Assertions.assertEquals(2, result.getListOfChidrenAndHouseholdWithOneAddress().size());
        Assertions.assertTrue(resultList.contains(child1AndHousehold));
        Assertions.assertTrue(resultList.contains(child2AndHousehold));
        Assertions.assertEquals("Tenley", firstNameChild1);
        Assertions.assertEquals(4, listOfHouseholdChild1.size());
        //Assertions.assertEquals(["John Boyd", "Jacob Boyd", "Roger Boyd", "Felicia Boyd"], listOfHouseholdChild1);
        Assertions.assertFalse(listOfHouseholdChild1.contains(firstNameChild1+" "+ lastNameChild1));
        Assertions.assertTrue(listOfHouseholdChild1.contains(firstNameChild2+" "+lastNameChild2));
        Assertions.assertEquals(5, child2AndHousehold.getAge());

    }

    @Test
    public void urlThreeTest(){
        HashMap<String, ArrayList<String>> result = urlService.urlThree("1");
        ArrayList listOfPhones = result.get("Phone numbers for firestation 1");
        Assertions.assertEquals(4, listOfPhones.size());

    }


    @Test
    public void urlFourTest(){
        /*Cette url doit retourner la liste des habitants vivant à l’adresse donnée ainsi que le numéro de la caserne
de pompiers la desservant. La liste doit inclure le nom, le numéro de téléphone, l'âge et les antécédents
médicaux (médicaments, posologie et allergies) de chaque personne.*/
        HouseholdUrl4 result = urlService.urlFour("908 73rd St");
        Assertions.assertEquals(2,result.getListOfPersons().size());
        Assertions.assertEquals("1",result.getFirestationNumber());
        Assertions.assertEquals("Reginold", result.getListOfPersons().get(0).getFirstName());
        Assertions.assertEquals("Peters", result.getListOfPersons().get(1).getLastName());
        Assertions.assertEquals(40, result.getListOfPersons().get(1).getAge());
        Assertions.assertEquals("841-874-7462", result.getListOfPersons().get(1).getPhoneNumber());
        Assertions.assertTrue(result.getListOfPersons().get(0).getMedicalRecordsForUrl4And5().getListOfAllergies().contains("illisoxian"));
        Assertions.assertTrue(result.getListOfPersons().get(0).getMedicalRecordsForUrl4And5().getMedications().containsKey("thradox"));


    }



    @Test
    public void urlFiveTest(){
        /*Cette url doit retourner une liste de tous les foyers desservis par la caserne. Cette liste doit regrouper les
        personnes par adresse. Elle doit aussi inclure le nom, le numéro de téléphone et l'âge des habitants, et
        faire figurer leurs antécédents médicaux (médicaments, posologie et allergies) à côté de chaque nom.*/

        ListOfHouseholdsCoveredByAFirestationUrl5 result =  urlService.urlFive("1,4");
        for(HouseholdUrl4 household : result.getListOfHouseholdsCoveredByAFirestationUrl5()){
            System.out.println(household);
        }
        //System.out.println(result.getListOfHouseholdsCoveredByAFirestationUrl5().);
        Assertions.assertEquals(5, result.getListOfHouseholdsCoveredByAFirestationUrl5().size());
        //644 Gershwin Cir > "Peter", "lastName":"Duncan",
        //489 Manchester St > "Lily", "lastName":"Cooper"
        //908 73rd St > Jamie", "lastName":"Peters"
        //112 Steppes Pl > "Ron", "lastName":"Peters",, Allison", "lastName":"Boyd"
        //947 E. Rose Dr "Brian", "lastName":"Stelzer", "address":"947 E. Rose Dr", "city":"Culver", "zip":"97451", "phone":"841-874-7784", "email":"bstel@email.com" },
        //        { "firstName":"Shawna", "lastName":"Stelzer", "address":"947 E. Rose Dr", "city":"Culver", "zip":"97451", "phone":"841-874-7784", "email":"ssanw@email.com" },
        //        { "firstName":"Kendrik", "lastName":"Stelzer", "address":"947 E. Rose Dr", "city":"Culver", "zip":"97451", "phone":"841-874-7784", "email":"bstel@email.com" },
        //        (steppes est à la fois 3 et 4)
    }

    @Test
    public void urlSixTest(){
        PersonInfoUrl6 result = urlService.urlSix ("Brian", "Stelzer");
        Assertions.assertEquals( "947 E. Rose Dr",result.getAddress());
        int age = personRepository.howOldIsThisPerson("12/06/1975");
        Assertions.assertEquals( age,result.getAge() );
        Assertions.assertEquals( "bstel@email.com",result.getMailAddress() );
        Assertions.assertTrue(result.getMedicalRecordsForUrl4And5().getMedications().containsKey("hydrapermazol") );
        Assertions.assertEquals("400mg", result.getMedicalRecordsForUrl4And5().getMedications().get("hydrapermazol") );

        Assertions.assertTrue(result.getMedicalRecordsForUrl4And5().getListOfAllergies().contains("nillacilan"));
    }//e nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,
    //posologie, allergies)

    @Test
    public void urlSevenTest(){
        ArrayList<String> result = urlService.urlSeven("Culver");
        Assertions.assertEquals(23, result.size());
    }

}
