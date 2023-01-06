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

import java.io.FileNotFoundException;

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

    public void setUp() throws FileNotFoundException {
        MakingModels makingModels = firestationRepository.getMakingModels();
        Any root = makingModels.modelMaker();
        listOfFirestationModels = firestationRepository.fillInFirestationModels(root);
    }

    @Test

    public void getFirestationsTest() {
        ListOfFirestationModels result = firestationService.getFirestations();
        Assertions.assertFalse(result.getListOfFirestationModels().size()==0);

    }
    @Test
    public void updateFirestationTest(){
        //String address = listOfFirestationModels.getListOfFirestationModels().get(5).getAddress();

        firestationService.updateFirestation("947 E. Rose Dr",  "new station number");
        Assertions.assertEquals("new station number", listOfFirestationModels.getListOfFirestationModels().get(10).getStation());
    }
    @Test
    public void deleteFirestationTest() {
        FirestationModel model = listOfFirestationModels.getListOfFirestationModels().get(5);
        String address = model.getAddress();
        String station= model.getStation();
        firestationService.deleteFirestation(address);
        Assertions.assertFalse(listOfFirestationModels.getListOfFirestationModels().contains(model));
    }
    @Test
    public void addFirestationTest(){
        FirestationModel added = new FirestationModel();
        added.setAddress("10 downing street");
        added.setStation("5");
        //listOfFirestationModels.getListOfFirestationModels().add(expected);
        firestationService.addFirestation("10 downing street", "5");
        Assertions.assertTrue(listOfFirestationModels.getListOfFirestationModels().contains(added));
    }

}
