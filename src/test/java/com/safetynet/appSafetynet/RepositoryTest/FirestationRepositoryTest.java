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
        Any root = makingModels.modelMaker();
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
    public void makeFirestationModelsTest(){
//ARRANGE
        // un fichier désérialisé en attribut de classe
        //
        // vérifier que medicalrecords n'est plus vide après passage de la méthode
//ACT
        //personRepository.makemedicalRecors models(Any deseralizedFile)
// ASSERT  l'attribut arrayList n'est pas vide
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
        firestationRepository.deleteOneAddressStationMapping(address);
        Assertions.assertFalse(listOfFirestationModels.getListOfFirestationModels().contains(model));
    }
    @Test
    public void addOneAddressStationMappingTest() {
//ARRANGE
        FirestationModel added = new FirestationModel();
        added.setAddress("10 downing street");
        added.setStation("5");
        //listOfFirestationModels.getListOfFirestationModels().add(expected);
        firestationRepository.addOneAddressStationMapping("10 downing street", "5");
//ACT

//ASSERT
        Assertions.assertTrue(listOfFirestationModels.getListOfFirestationModels().contains(added));
    }
    @Test
    public void updateFirestationNumberForAddressTest(){
        String address = listOfFirestationModels.getListOfFirestationModels().get(5).getAddress();

        firestationRepository.updateFirestationNumberForAnAddress(address, "new station number");
        Assertions.assertEquals("new station number", listOfFirestationModels.getListOfFirestationModels().get(5).getStation());
//ARRANGE
//ACT
//ASSERT
    }


}
