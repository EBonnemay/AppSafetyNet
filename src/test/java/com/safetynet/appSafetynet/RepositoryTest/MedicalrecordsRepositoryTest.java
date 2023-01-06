package com.safetynet.appSafetynet.RepositoryTest;


import com.jsoniter.any.Any;
import com.safetynet.appSafetynet.model.ListOfMedicalrecordsModels;
import com.safetynet.appSafetynet.model.MedicalrecordsModel;
import com.safetynet.appSafetynet.repository.MakingModels;
import com.safetynet.appSafetynet.repository.MedicalrecordsRepository;
import org.junit.jupiter.api.*;
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
       MakingModels makingModels = medicalrecordsRepository.getMakingModels();
       Any root = makingModels.modelMaker();
       listOfMedicalrecordsModels = medicalrecordsRepository.fillInMedicalrecordsModels(root);
    }




   @Test
   public void fillInMedicalRecordsModelsTest(){
       listOfMedicalrecordsModels.getListOfMedicalrecordsModels().clear();
       MakingModels makingModels = medicalrecordsRepository.getMakingModels();
       Any root = makingModels.modelMaker();
       medicalrecordsRepository.fillInMedicalrecordsModels(root);
       listOfMedicalrecordsModels = medicalrecordsRepository.fillInMedicalrecordsModels(root);

        Assertions.assertTrue(listOfMedicalrecordsModels.getListOfMedicalrecordsModels().size()>0);
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
        medicalrecordsRepository.deleteOneMedicalRecord(firstName+" "+lastName);
        Assertions.assertFalse(listOfMedicalrecordsModels.getListOfMedicalrecordsModels().contains(model));
    }
    @Test
    public void addMedicalrecordsModelTest() {
        MedicalrecordsModel added = new MedicalrecordsModel();
        added.setFirstName("Emma");
        added.setLastName("Bovary");
        listOfMedicalrecordsModels.getListOfMedicalrecordsModels().add(added);
        Assertions.assertTrue(listOfMedicalrecordsModels.getListOfMedicalrecordsModels().contains(added));

    }
    @Test
    @DisplayName("update Allergies-add an allergy to the list")
    public void updateAllergiesAddNewAllergyTest(){

        //"medications":["aznol:200mg"], "allergies":["nillacilan"]
        MedicalrecordsModel allisonFile = listOfMedicalrecordsModels.getListOfMedicalrecordsModels().get(17);
        medicalrecordsRepository.updateAllergiesOrMeds("Allison Boyd",  "allergies",  "add",  "noisette");

        Assertions.assertTrue(allisonFile.getAllergies().contains("noisette"));


    }
    @Test
    @DisplayName("update Allergies-add an allergy to the list but allergy already in the list. check it appears just once")
    public void updateAllergiesAddExistingAllergyTest(){
        //Allison Boyd, index 17
        MedicalrecordsModel allisonFile = listOfMedicalrecordsModels.getListOfMedicalrecordsModels().get(17);
        int occurrence = 0;
        medicalrecordsRepository.updateAllergiesOrMeds("Allison Boyd",  "allergies",  "add","nillacilan");

        for (String allergy : allisonFile.getAllergies()) {
            if (allergy.equals("nillacilan")) {
                occurrence++;
            }
        }
        Assertions.assertTrue(allisonFile.getAllergies().contains("nillacilan"));
        Assertions.assertTrue(occurrence==1);

    }

    @Test
    @DisplayName("update Allergies - remove an allergy from the list")
    public void updateAllergiesRemoveTest(){

        //"medications":["aznol:200mg"], "allergies":["nillacilan"]
        //Allison Boyd, index 17
        medicalrecordsRepository.updateAllergiesOrMeds("Allison Boyd",  "allergies",  "delete",  "nillacilan");

        Assertions.assertFalse(listOfMedicalrecordsModels.getListOfMedicalrecordsModels().get(17).getAllergies().contains("nillacilan"));
    }

   @Test
   @DisplayName("update Medications - add a new medicine and posology")
    public void updateMedicationsAddTest(){
        //Sophia Zemicks, index 11
       //"medications":["aznol:60mg", "hydrapermazol:900mg", "pharmacol:5000mg", "terazine:500mg"], "allergies":["peanut", "shellfish", "aznol"] },
        MedicalrecordsModel sophiaFile = listOfMedicalrecordsModels.getListOfMedicalrecordsModels().get(11);

       medicalrecordsRepository.updateAllergiesOrMeds("Sophia Zemicks",  "medications",  "add",  "Doliprane=1g");

       Assertions.assertTrue(listOfMedicalrecordsModels.getListOfMedicalrecordsModels().get(11).getMedications().containsKey("Doliprane"));
       Assertions.assertTrue(listOfMedicalrecordsModels.getListOfMedicalrecordsModels().get(11).getMedications().containsValue("1g"));
   }

    @Test
    @DisplayName("update Medications - change pososlogy of a medicine already in the patient's file, and check if posology is replaced")
   public void updateMedicationChangePosologyTest(){
        //Sophia Zemicks, index 11
        // "medications":["aznol:60mg", "hydrapermazol:900mg", "pharmacol:5000mg", "terazine:500mg"], "allergies":["peanut", "shellfish", "aznol"] },


        medicalrecordsRepository.updateAllergiesOrMeds("Sophia Zemicks",  "medications",  "add",  "pharmacol=5mg");
        Assertions.assertEquals("5mg", listOfMedicalrecordsModels.getListOfMedicalrecordsModels().get(11).getMedications().get("pharmacol"));

    }

    @Test
    @DisplayName("update Medications remove a medication from the map of medications")
    public void updateMedicationRemoveMedTest(){

        //Sophia Zemicks, index 11
        // "medications":["aznol:60mg", "hydrapermazol:900mg", "pharmacol:5000mg", "terazine:500mg"], "allergies":["peanut", "shellfish", "aznol"] },

        medicalrecordsRepository.updateAllergiesOrMeds("Sophia Zemicks",  "medications",  "delete",  "aznol");
        Assertions.assertFalse(listOfMedicalrecordsModels.getListOfMedicalrecordsModels().get(11).getMedications().containsKey("aznol"));
        Assertions.assertTrue(listOfMedicalrecordsModels.getListOfMedicalrecordsModels().get(11).getAllergies().contains("aznol"));

    }


}
