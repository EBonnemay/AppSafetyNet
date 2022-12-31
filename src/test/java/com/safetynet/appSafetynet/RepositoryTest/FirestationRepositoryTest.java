package com.safetynet.appSafetynet.RepositoryTest;

import com.safetynet.appSafetynet.repository.FirestationRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class FirestationRepositoryTest {
    private static FirestationRepository firestationRepository;


    @BeforeAll
    //désérialiser le fichier data
    public static void setUp() throws FileNotFoundException {

        firestationRepository= new FirestationRepository();
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
            //ArrayList persons remplie en attribut de classe
//ACT
//ASSERT

    }
    @Test
    public void updateFirestationNumberForAddressTest(){
//ARRANGE
//ACT
//ASSERT
    }


}
