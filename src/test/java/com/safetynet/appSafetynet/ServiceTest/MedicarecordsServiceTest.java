package com.safetynet.appSafetynet.ServiceTest;

import com.jsoniter.any.Any;
import com.safetynet.appSafetynet.model.ListOfMedicalrecordsModels;
import com.safetynet.appSafetynet.model.MedicalrecordsModel;
import com.safetynet.appSafetynet.repository.MakingModels;
import com.safetynet.appSafetynet.repository.MedicalrecordsRepository;
import com.safetynet.appSafetynet.service.MedicalrecordsService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MedicarecordsServiceTest {
    @Autowired
    MedicalrecordsRepository medicalrecordsRepository;

    @Autowired
    ListOfMedicalrecordsModels listOfMedicalrecordsModels;

    @Autowired
    MedicalrecordsService medicalrecordsService;

    @BeforeEach

    public void setUp() {
        MakingModels makingModels = medicalrecordsRepository.getMakingModels();
        Any root  = makingModels.modelMaker("classpath:data.json");

        listOfMedicalrecordsModels = medicalrecordsRepository.fillInMedicalrecordsModels(root);
    }



    @Test
    public void getMedicalModelsTest(){

        Assertions.assertEquals(23, medicalrecordsService.getMedicalrecords().getListOfMedicalrecordsModels().size());
    }
    @Test
    public void deleteMedicalrecordsModelTest(){
        MedicalrecordsModel model = listOfMedicalrecordsModels.getListOfMedicalrecordsModels().get(2);
        String firstName = model.getFirstName();
        String lastName = model.getLastName();
        listOfMedicalrecordsModels = medicalrecordsService.deleteMedicalRecordsModel(firstName+" "+lastName);
        Assertions.assertFalse(listOfMedicalrecordsModels.getListOfMedicalrecordsModels().contains(model));
    }
    @Test
    public void addMedicalrecordsModelTest() {
        MedicalrecordsModel added = new MedicalrecordsModel();
        added.setFirstName("Emma");
        added.setLastName("Bovary");
        //listOfMedicalrecordsModels.getListOfMedicalrecordsModels().add(added);
        listOfMedicalrecordsModels = medicalrecordsService.addMedicalrecordsModel(added);
        Assertions.assertTrue(listOfMedicalrecordsModels.getListOfMedicalrecordsModels().contains(added));

    }
    @Test
    @DisplayName("update Allergies-add an allergy to the list")
    public void updateMedicationPosology(){
        MedicalrecordsModel allisonFile = listOfMedicalrecordsModels.getListOfMedicalrecordsModels().get(17);
        HashMap<String, String> map = new HashMap<>();
        map.put("aznol", "500mg");
        allisonFile.setMedications(map);
        MedicalrecordsModel result = medicalrecordsService.updateMedicalrecords(allisonFile);
        Assertions.assertEquals("500mg", result.getMedications().get("aznol"));
    }
}







