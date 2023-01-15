package com.safetynet.appSafetynet.RepositoryTest;


import com.jsoniter.any.Any;
import com.jsoniter.spi.JsonException;
import com.safetynet.appSafetynet.model.ListOfMedicalrecordsModels;
import com.safetynet.appSafetynet.model.MedicalrecordsModel;
import com.safetynet.appSafetynet.repository.MakingModels;
import com.safetynet.appSafetynet.repository.MedicalrecordsRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MedicalrecordsRepositoryTest {
    @Autowired
    MedicalrecordsRepository medicalrecordsRepository;

    @Autowired
    ListOfMedicalrecordsModels listOfMedicalrecordsModels;



    @BeforeEach

    public void setUp() {
       MakingModels makingModels = medicalrecordsRepository.getMakingModels();
       Any root = makingModels.modelMaker("classpath:data.json");

       listOfMedicalrecordsModels = medicalrecordsRepository.fillInMedicalrecordsModels(root);
    }




   @Test
   public void fillInMedicalRecordsModelsTest(){
       listOfMedicalrecordsModels.getListOfMedicalrecordsModels().clear();
       MakingModels makingModels = medicalrecordsRepository.getMakingModels();
       Any root = makingModels.modelMaker("classpath:data.json");

       medicalrecordsRepository.fillInMedicalrecordsModels(root);
       listOfMedicalrecordsModels = medicalrecordsRepository.fillInMedicalrecordsModels(root);

        Assertions.assertTrue(listOfMedicalrecordsModels.getListOfMedicalrecordsModels().size()>0);
    }

    @Test
    public void fillInMedicalrecordsModelsTestIfNoStringMedicalmodelFound (){
        listOfMedicalrecordsModels.getListOfMedicalrecordsModels().clear();
        MakingModels makingModels = medicalrecordsRepository.getMakingModels();
        Any root = makingModels.modelMaker("classpath:dataWithWrongKeysForTest.json");

        Assertions.assertThrows(JsonException.class, ()-> medicalrecordsRepository.fillInMedicalrecordsModels(root));

    }
    @Test
    public void findAllMedicalmodelsTest(){
        listOfMedicalrecordsModels = medicalrecordsRepository.findAll();
        Assertions.assertEquals(23, medicalrecordsRepository.findAll().getListOfMedicalrecordsModels().size());
    }
    @Test
    public void deleteMedicalrecordsModelTest(){
        MedicalrecordsModel model = listOfMedicalrecordsModels.getListOfMedicalrecordsModels().get(5);
        String firstName = model.getFirstName();
        String lastName = model.getLastName();
        listOfMedicalrecordsModels = medicalrecordsRepository.deleteOneMedicalRecord(firstName+" "+lastName);
        Assertions.assertFalse(listOfMedicalrecordsModels.getListOfMedicalrecordsModels().contains(model));
    }
    @Test
    public void deleteNonExistentMedicarecordsModelTest(){
        MedicalrecordsModel model = new MedicalrecordsModel();
        model.setFirstName("Emma");
        model.setLastName("Bovary");
        Assertions.assertThrows(RuntimeException.class, () -> medicalrecordsRepository.deleteOneMedicalRecord(model.getFirstName()+" "+model.getLastName()));
    }

    @Test
    public void addMedicalrecordsModelTest() {
        MedicalrecordsModel added = new MedicalrecordsModel();
        added.setFirstName("Emma");
        added.setLastName("Bovary");

        listOfMedicalrecordsModels = medicalrecordsRepository.addOneMedicalRecords(added);
        Assertions.assertTrue(listOfMedicalrecordsModels.getListOfMedicalrecordsModels().contains(added));

    }
    @Test
    public void addAlreadyExistentMedicalrecordsModelTest() {
        MedicalrecordsModel existing = listOfMedicalrecordsModels.getListOfMedicalrecordsModels().get(4);

        Assertions.assertThrows(RuntimeException.class, () ->  medicalrecordsRepository.addOneMedicalRecords(existing));
    }

    @Test
    @DisplayName("update Allergies-add an allergy to the list")
    public void updateMedicalrecordsTest(){
        MedicalrecordsModel model = new MedicalrecordsModel();
        model.setFirstName("Allison");
        model.setLastName("Boyd");
        ArrayList<String> list = new ArrayList<>();
        list.add("nuts");
        list.add("apple");
        model.setAllergies(list);
        model.setBirthdate("31/10/2017");
        MedicalrecordsModel result = medicalrecordsRepository.updateMedicalrecords(model);

        Assertions.assertEquals(model, result);
        Assertions.assertTrue(listOfMedicalrecordsModels.getListOfMedicalrecordsModels().get(17).getAllergies().contains("nuts"));

    }
    @Test
    @DisplayName("update non existent Medical File")
    public void updateNonExistentMedicalFile(){
        MedicalrecordsModel model = new MedicalrecordsModel();
        model.setFirstName("Nathalie");
        model.setLastName("Boyd");
        ArrayList<String> list = new ArrayList<>();
        list.add("nuts");
        list.add("apple");
        model.setAllergies(list);
        model.setBirthdate("31/10/2017");

        Assertions.assertThrows(RuntimeException.class, () -> medicalrecordsRepository.updateMedicalrecords(model));

    }



}
