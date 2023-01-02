package com.safetynet.appSafetynet.RepositoryTest;

import com.safetynet.appSafetynet.model.FirestationModel;
import com.safetynet.appSafetynet.model.ListOfFirestationModels;
import com.safetynet.appSafetynet.repository.FirestationRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FirestationRepositoryTest {

    @Autowired
    FirestationRepository firestationRepository;

    @Autowired
    ListOfFirestationModels listOfFirestationModels;

    @BeforeAll
    //désérialiser le fichier data
    public void setUp() throws FileNotFoundException {

        firestationRepository.setUpListOfFirestationsModel();
    }


    @Tag("")
    @DisplayName("")

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
        //FAUT IL APPELER LA METHODE MAKEPERSONMODELTESTS DANS LES AUTRES?
        //ArrayList persons remplie en attribut de classe
        //étant donnée une arrayList remplie de medicalRecords, est ce que arrayList retourné par la méthode = arraylist attribut de classe?
    }
    @Test
    public void findAddressesServedByOneStationTest(){
//ARRANGE
        //ArrayList persons remplie en attribut de classe
//ACT
//ASSERT
    }
    @Test
    public void deleteOneAddressStationMappingTest() {
//ARRANGE
        //ArrayList persons remplie en attribut de classe
//ACT
//ASSERT
    }
        @Test
        public void addOneAddressStationMappingTest() {
//ARRANGE
            FirestationModel expected = new FirestationModel();
            expected.setAddress("10 downing street");
            expected.setStation("5");
            listOfFirestationModels.getListOfFirestationModels().add(expected);

//ACT

//ASSERT
            Assertions.assertTrue(listOfFirestationModels.getListOfFirestationModels().contains(expected));
    }
    @Test
    public void updateFirestationNumberForAddressTest(){
//ARRANGE
//ACT
//ASSERT
    }


}
