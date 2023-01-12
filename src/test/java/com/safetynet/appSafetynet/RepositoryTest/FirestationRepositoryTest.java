package com.safetynet.appSafetynet.RepositoryTest;

import com.jsoniter.any.Any;
import com.safetynet.appSafetynet.model.FirestationModel;
import com.safetynet.appSafetynet.model.ListOfFirestationModels;
import com.safetynet.appSafetynet.repository.FirestationRepository;
import com.safetynet.appSafetynet.repository.MakingModels;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.util.ArrayList;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FirestationRepositoryTest {
//
    @Autowired
    FirestationRepository firestationRepository;

    @Autowired
    ListOfFirestationModels listOfFirestationModels;
   // @Autowired
    //MakingModels makingModels;



    @BeforeEach

    public void setUp() throws FileNotFoundException {
        MakingModels makingModels = firestationRepository.getMakingModels();
        Any root;
        root = makingModels.modelMaker();

        listOfFirestationModels = firestationRepository.fillInFirestationModels(root);

    }



    @DisplayName("soit une listOfFirestations avec un attribut 'liste' vide, lorsque la méthode setUpListOfFirestationModels est appelée, alors elle remplit la liste de l'objet'listOfFirestationModels'")
    @Test
    public void fillInFirestationModelsTest (){
        listOfFirestationModels.getListOfFirestationModels().clear();
        MakingModels makingModels = firestationRepository.getMakingModels();
        Any root = makingModels.modelMaker();

        //listOfFirestationModels = firestationRepository.fillInFirestationModels(root);
        listOfFirestationModels = firestationRepository.fillInFirestationModels(root);

        Assertions.assertTrue(listOfFirestationModels.getListOfFirestationModels().size()>0);
    }



    @Test
    public void findAllFirestationModelsTest(){
        listOfFirestationModels = firestationRepository.findAll();
        System.out.println(listOfFirestationModels.getListOfFirestationModels());
        Assertions.assertEquals(13, firestationRepository.findAll().getListOfFirestationModels().size());


    }
    @Test
    public void findAddressesServedByOneStationTest(){
        ArrayList<String> listOfStations = firestationRepository.findAddressesServedByOneStation("3", listOfFirestationModels);
        Assertions.assertEquals(4, listOfStations.size());
    }


    @Test
    public void deleteOneAddressStationMappingTest() {
        FirestationModel model = listOfFirestationModels.getListOfFirestationModels().get(5);
        String address = model.getAddress();
        String station= model.getStation();
        firestationRepository.deleteOneAddressStationMappingWithAddressParam(address);
        Assertions.assertFalse(listOfFirestationModels.getListOfFirestationModels().contains(model));
    }
    @Test
    public void deleteNonExistingAddressStationMappingTest() {
        FirestationModel model = new FirestationModel();
        model.setAddress("3 Sailor Road");
        model.setStation("2");

        Assertions.assertThrows(RuntimeException.class, ()-> firestationRepository.deleteOneAddressStationMappingWithAddressParam("3 Sailor Road"));
    }
    @Test
    public void addOneAddressStationMappingTest() {
//ARRANGE
        FirestationModel added = new FirestationModel();
        added.setAddress("10 downing street");
        added.setStation("5");
        //listOfFirestationModels.getListOfFirestationModels().add(expected);
        firestationRepository.addOneAddressStationMapping(added);
        Assertions.assertTrue(listOfFirestationModels.getListOfFirestationModels().contains(added));
    }
    @Test
    public void addAlreadyExistingAddressStationMappingTest(){
        FirestationModel model = new FirestationModel();
        model.setAddress("644 Gershwin Cir");
        model.setStation("1");

        Assertions.assertThrows(RuntimeException.class, () -> firestationRepository.addOneAddressStationMapping(model));

    }
    @Test
    public void updateFirestationNumberWithExistingAddressTestAndDifferentFirestationNumber(){
        FirestationModel model = new FirestationModel();
        model.setAddress("112 Steppes Pl");
        model.setStation("new station number");
        //String address = listOfFirestationModels.getListOfFirestationModels().get(5).getAddress();

        firestationRepository.updateFirestationNumberForAnAddress(model);
        Assertions.assertEquals("new station number", listOfFirestationModels.getListOfFirestationModels().get(5).getStation());
//ARRANGE
//ACT
//ASSERT
    }
    @Test
    public void updateFirestationNumberForAddressTestWithNonExistingAddress(){
        FirestationModel model = new FirestationModel();
        model.setAddress("12 Flower St");
        model.setStation("2");
        RuntimeException r = new RuntimeException();
        Assertions.assertThrows(RuntimeException.class, () -> firestationRepository.updateFirestationNumberForAnAddress(model));

    }

}
