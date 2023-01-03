package com.safetynet.appSafetynet.RepositoryTest;


import com.safetynet.appSafetynet.model.ListOfMedicalrecordsModels;
import com.safetynet.appSafetynet.repository.MedicalrecordsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MedicalrecordsRepositoryTest {
    @Autowired
    MedicalrecordsRepository medicalrecordsRepository;

    @Autowired
    ListOfMedicalrecordsModels listOfMedicalrecordsModels;



    @BeforeEach

    public void setUp() throws FileNotFoundException {
        //medicalrecordsRepository.setUpListOfMedicalrecordsModel();
      // MakingModels makingModels = medicalrecordsRepository.getMakingModels();
    }




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
