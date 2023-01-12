package com.safetynet.appSafetynet.repository;

import com.jsoniter.any.Any;
import com.jsoniter.spi.JsonException;
import com.safetynet.appSafetynet.model.ListOfMedicalrecordsModels;
import com.safetynet.appSafetynet.model.MedicalrecordsModel;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@Repository
public class MedicalrecordsRepository implements IMedicalrecordsRepository {
    @Autowired
    MakingModels makingModels;
    ListOfMedicalrecordsModels listOfMedicalrecordsModels = new ListOfMedicalrecordsModels();
    Any root;
    static final Logger logger = LogManager.getLogger();

    public MedicalrecordsRepository() throws FileNotFoundException {
    }

    @Override
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
        } catch(JsonException e){
            logger.error("the key 'medicalrecords' was not found in deserialized file");
            throw new JsonException("key not found", e);

        }catch(Exception e){
            logger.error("filling Medicalrecords models failed");
            throw new RuntimeException("Medicalrecords model could not get filled in ",e);
        }
        return listOfMedicalrecordsModels;
    }

    @Override
    public void setUpListOfMedicalrecordsModel() {
        if (listOfMedicalrecordsModels.getListOfMedicalrecordsModels().size() == 0) {
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

    @Override
    public void addOneMedicalRecords(MedicalrecordsModel element) {
        setUpListOfMedicalrecordsModel();
        if(!listOfMedicalrecordsModels.getListOfMedicalrecordsModels().contains(element)){
            listOfMedicalrecordsModels.getListOfMedicalrecordsModels().add(element);
        }
        else{
            logger.error("the medical record to add is already in the data. No adding");
            throw new RuntimeException("no adding");
        }

    }

    @Override
    public void deleteOneMedicalRecord(String firstLastName) {
        setUpListOfMedicalrecordsModel();
        int match = 0;
        for (int i = 0; i < listOfMedicalrecordsModels.getListOfMedicalrecordsModels().size(); i++) {
            MedicalrecordsModel element = listOfMedicalrecordsModels.getListOfMedicalrecordsModels().get(i);
            if ((element.getFirstName() + " " + element.getLastName()).equals(firstLastName)) {
                match = 1;
                listOfMedicalrecordsModels.getListOfMedicalrecordsModels().remove(element);

            }

        }
        if(match == 0) {
            logger.error("the medical record to delete is not in the data. No deleting");
            throw new RuntimeException("delete failed");
        }
    }

    @Override
    public void updateAllergiesOrMeds(MedicalrecordsModel model) {
        setUpListOfMedicalrecordsModel();
        int match = 0;
        for (int i = 0; i < listOfMedicalrecordsModels.getListOfMedicalrecordsModels().size(); i++) {
            if (listOfMedicalrecordsModels.getListOfMedicalrecordsModels().get(i).getFirstName().equals(model.getFirstName())
                    && listOfMedicalrecordsModels.getListOfMedicalrecordsModels().get(i).getLastName().equals(model.getLastName())) {
                match = 1;
                listOfMedicalrecordsModels.getListOfMedicalrecordsModels().set(i, model);
            }
        }
        if(match==0){
            logger.error("the medical record to update is not in the data. No updating");
            throw new RuntimeException("update failed");
        }
    }
}
