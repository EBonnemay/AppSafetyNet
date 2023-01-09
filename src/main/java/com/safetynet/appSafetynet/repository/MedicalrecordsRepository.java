package com.safetynet.appSafetynet.repository;

import com.jsoniter.any.Any;
import com.jsoniter.spi.JsonException;
import com.safetynet.appSafetynet.model.ListOfMedicalrecordsModels;
import com.safetynet.appSafetynet.model.MedicalrecordsModel;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@Repository
public class MedicalrecordsRepository {
    @Autowired
    MakingModels makingModels;
    ListOfMedicalrecordsModels listOfMedicalrecordsModels = new ListOfMedicalrecordsModels();
    Any root;

    public MedicalrecordsRepository() throws FileNotFoundException {
    }

    public ListOfMedicalrecordsModels fillInMedicalrecordsModels(Any deserializedFile) {
        List<MedicalrecordsModel> attributeList = new ArrayList<>();
        try {
            //Any deserializedFile = makingModels.modelMaker();
            Any json_medicalrecords = deserializedFile.get("medicalrecords");
            List<Any> list = json_medicalrecords.asList();

            for (int i = 0; i < list.size(); i++) {
                MedicalrecordsModel model = new MedicalrecordsModel();
                model.setFirstName(list.get(i).get("firstName").toString());
                model.setLastName(list.get(i).get("lastName").toString());
                model.setBirthdate(list.get(i).get("birthdate").toString());
                HashMap<String, String> mapOfMed = new HashMap<>();
                Any json_listOfMed = list.get(i).get("medications");
                for (Any element : json_listOfMed) {
                    String med = element.toString();
                    int index = med.indexOf(":");
                    String medName = med.substring(0, index);
                    String medQuantity = med.substring(index + 1);
                    mapOfMed.put(medName, medQuantity);
                }
                model.setMedications(mapOfMed);
                ArrayList<String> listOfAllergies = new ArrayList<>();
                Any json_listOfAll = list.get(i).get("allergies");
                for (Any element : json_listOfAll) {
                    String all = element.toString();
                    listOfAllergies.add(all);
                }
                model.setAllergies(listOfAllergies);
                attributeList.add(model);
            }
            listOfMedicalrecordsModels.setListOfMedicalrecordsModels(attributeList);
        }catch(JsonException e){
            System.out.println("medicalrecords not found in file");
        }catch (Exception e) {
            System.out.println("problems filling models");
        }
        return listOfMedicalrecordsModels;
    }
    public void setUpListOfMedicalrecordsModel(){
        if (listOfMedicalrecordsModels.getListOfMedicalrecordsModels().size()==0) {
            if (root == null) {
                root = makingModels.modelMaker();
            }
            listOfMedicalrecordsModels = fillInMedicalrecordsModels(root);
            System.out.println("listOfMedicalrecordsModels filled in");
        }
    }
    public ListOfMedicalrecordsModels findAll() {
        setUpListOfMedicalrecordsModel();
        return listOfMedicalrecordsModels;
    }
    public void addOneMedicalRecords(MedicalrecordsModel element) {
        setUpListOfMedicalrecordsModel();
        listOfMedicalrecordsModels.getListOfMedicalrecordsModels().add(element);
    }
    public void deleteOneMedicalRecord(String firstLastName) {
        setUpListOfMedicalrecordsModel();
        for(int i = 0; i<listOfMedicalrecordsModels.getListOfMedicalrecordsModels().size(); i++){
            MedicalrecordsModel element = listOfMedicalrecordsModels.getListOfMedicalrecordsModels().get(i);
            if ((element.getFirstName() + " " + element.getLastName()).equals(firstLastName)) {
                listOfMedicalrecordsModels.getListOfMedicalrecordsModels().remove(element);

            }
        }

    }
    // mettre à jour un dossier médical existant (comme évoqué précédemment, supposer que le
    //prénom et le nom de famille ne changent pas) ;


    public void updateAllergiesOrMeds(MedicalrecordsModel model) {
        setUpListOfMedicalrecordsModel();
        List<MedicalrecordsModel> AttributeList = new ArrayList<>();
        for(int i = 0; i<listOfMedicalrecordsModels.getListOfMedicalrecordsModels().size(); i++){
            listOfMedicalrecordsModels.getListOfMedicalrecordsModels().set(i, model);
        }

    }

}

