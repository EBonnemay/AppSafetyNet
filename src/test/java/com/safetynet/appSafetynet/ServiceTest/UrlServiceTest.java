package com.safetynet.appSafetynet.ServiceTest;

import com.jsoniter.any.Any;
import com.safetynet.appSafetynet.model.ListOfFirestationModels;
import com.safetynet.appSafetynet.model.ListOfMedicalrecordsModels;
import com.safetynet.appSafetynet.model.ListOfPersonModels;
import com.safetynet.appSafetynet.model.dto.ChildAndHisHouseholdForUrl2;
import com.safetynet.appSafetynet.model.dto.ListOfChildrenAndTheHouseholdWithOneAddressUrl2;
import com.safetynet.appSafetynet.model.dto.ListOfPersonsCoveredByAFirestationUrl1;
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
    ListOfPersonModels listOfPersonModels;




    @BeforeAll

    //désérialiser le fichier data
    public void setUp() throws FileNotFoundException {
        MakingModels makingModels = firestationRepository.getMakingModels();
        Any root = makingModels.modelMaker();
        listOfFirestationModels = firestationRepository.fillInFirestationModels(root);
        listOfMedicalrecordsModels = medicalrecordsRepository.fillInMedicalrecordsModels(root);
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
        //FAUT IL APPELER LA METHODE MAKEPERSONMODELTESTS DANS LES AUTRES?
        //ArrayList persons remplie en attribut de classe
        //étant donnée une arrayList remplie de personModels, est ce que arrayList retourné par la méthode = arraylist attribut de classe?
    }



    @Test
    public void urlFiveTest(){
        //FAUT IL APPELER LA METHODE MAKEPERSONMODELTESTS DANS LES AUTRES?
        //ArrayList persons remplie en attribut de classe
        //étant donnée une arrayList remplie de personModels, est ce que arrayList retourné par la méthode = arraylist attribut de classe?
    }

    @Test
    public void urlSixTest(){
        //FAUT IL APPELER LA METHODE MAKEPERSONMODELTESTS DANS LES AUTRES?
        //ArrayList persons remplie en attribut de classe
        //étant donnée une arrayList remplie de personModels, est ce que arrayList retourné par la méthode = arraylist attribut de classe?
    }

    @Test
    public void urlSevenTest(){
        ArrayList<String> result = urlService.urlSeven("Culver");
        Assertions.assertEquals(23, result.size());
    }

}
