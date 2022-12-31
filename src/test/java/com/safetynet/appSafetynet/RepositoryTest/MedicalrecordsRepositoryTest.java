package com.safetynet.appSafetynet.RepositoryTest;


import com.safetynet.appSafetynet.repository.MedicalrecordsRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class MedicalrecordsRepositoryTest {
    private static MedicalrecordsRepository medicalrecordsRepository;


    @BeforeAll
    //désérialiser le fichier data
    public static void setUp() throws FileNotFoundException {

        medicalrecordsRepository= new MedicalrecordsRepository();
    }


    @Tag("")
    @DisplayName("")

    @Test
    public void makeMedicalrecordsModelsTest(){
//ARRANGE
        // un fichier désérialisé en attribut de classe
        //
        // vérifier que medicalrecords n'est plus vide après passage de la méthode
//ACT
        //personRepository.makemedicalRecors models(Any deseralizedFile)
// ASSERT  l'attribut arrayList n'est pas vide
    }


    @Test
    public void findAllMedicalmodelsTest(){
        //FAUT IL APPELER LA METHODE MAKEPERSONMODELTESTS DANS LES AUTRES?
        //ArrayList persons remplie en attribut de classe
        //étant donnée une arrayList remplie de medicalRecords, est ce que arrayList retourné par la méthode = arraylist attribut de classe?
    }
    @Test
    public void deleteMedicalrecordsModelTest(){
//ARRANGE
        //ArrayList persons remplie en attribut de classe
//ACT
//ASSERT
    }
    @Test
    public void addMedicalrecordsModelTest() {
//ARRANGE
        //ArrayList persons remplie en attribut de classe
//ACT
//ASSERT

    }
    @Test
    public void updateMedicalrecordsTest(){
//ARRANGE
//ACT
//ASSERT
    }


}
