package com.safetynet.appSafetynet.ServiceTest;

import com.jsoniter.any.Any;
import com.safetynet.appSafetynet.model.FirestationModel;
import com.safetynet.appSafetynet.model.ListOfFirestationModels;
import com.safetynet.appSafetynet.repository.FirestationRepository;
import com.safetynet.appSafetynet.repository.MakingModels;
import com.safetynet.appSafetynet.service.FirestationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FirestationServiceTest {
    @Autowired
    FirestationService firestationService;
    @Autowired
    FirestationRepository firestationRepository;

    @Autowired
    ListOfFirestationModels listOfFirestationModels;


    @BeforeEach

    public void setUp() {
        MakingModels makingModels = firestationRepository.getMakingModels();
        Any root = makingModels.modelMaker("classpath:data.json");

        listOfFirestationModels = firestationRepository.fillInFirestationModels(root);
    }

    @Test

    public void getFirestationsTest() {
        ListOfFirestationModels result = firestationService.getFirestations();
        Assertions.assertNotEquals(0, result.getListOfFirestationModels().size());

    }
    @Test
    public void updateFirestationTest(){
        //String address = listOfFirestationModels.getListOfFirestationModels().get(5).getAddress();
        FirestationModel model = new FirestationModel();
        model.setAddress("947 E. Rose Dr");
        model.setStation("new station number");
        firestationService.updateFirestation(model);
        Assertions.assertEquals("new station number", listOfFirestationModels.getListOfFirestationModels().get(10).getStation());
    }
    @Test
    public void deleteFirestationWithAddressParamTest() {
        FirestationModel model = listOfFirestationModels.getListOfFirestationModels().get(5);
        String param = "address";
        String addressContent = model.getAddress();
        firestationService.deleteFirestation(param, addressContent);
        Assertions.assertFalse(listOfFirestationModels.getListOfFirestationModels().contains(model));
    }
    @Test
    public void deleteFirestationWithFirestationNumberParamTest() {
        String param = "firestationNumber";
        String fireNumber = "4";
        firestationService.deleteFirestation(param, fireNumber);
        Assertions.assertEquals(11,listOfFirestationModels.getListOfFirestationModels().size());
    }
    @Test
    public void addFirestationTest(){
        FirestationModel added = new FirestationModel();
        added.setAddress("10 downing street");
        added.setStation("5");
        //listOfFirestationModels.getListOfFirestationModels().add(expected);
        firestationService.addFirestation(added);
        Assertions.assertTrue(listOfFirestationModels.getListOfFirestationModels().contains(added));
    }

}
