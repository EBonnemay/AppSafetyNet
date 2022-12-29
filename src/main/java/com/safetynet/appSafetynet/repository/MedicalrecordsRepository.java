package com.safetynet.appSafetynet.repository;

import com.jsoniter.any.Any;
import com.safetynet.appSafetynet.model.MedicalrecordsModel;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@Repository
public class MedicalrecordsRepository {
    @Autowired
    MakingModels makingModels;
    ArrayList<MedicalrecordsModel> arrayListMedicalrecords = new ArrayList<>();
    Any root;

    public MedicalrecordsRepository() throws FileNotFoundException {
    }

    public void makeMedicalrecordsModels(Any deserializedFile) {
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

                arrayListMedicalrecords.add(model);
            }
        } catch (Exception e) {
            System.out.println("problems filling models");
        }
    }

    public Iterable<MedicalrecordsModel> findAll() {
        //arrayListMedicalrecords.clear();
        if (arrayListMedicalrecords.isEmpty()) {
            if (root == null){
                root = makingModels.modelMaker();
            }
            makeMedicalrecordsModels(root);
        }
        return arrayListMedicalrecords;
    }

    public MedicalrecordsModel findMedicalRecordsForOnePerson(String firstLastName) {
        if (arrayListMedicalrecords.isEmpty()) {
            if(root == null){
                root = makingModels.modelMaker();
            }
            makeMedicalrecordsModels(root);
        }
        for (MedicalrecordsModel element : arrayListMedicalrecords) {
            if ((element.getFirstName() + " " +element.getLastName()).equals(firstLastName)) {
                return element;
            }
        }
        return null;
    }

    //ajouter un dossier médical ;
    public void addOneMedicalRecords(MedicalrecordsModel element) {
        if (arrayListMedicalrecords.isEmpty()) {
            if (root == null){
                root = makingModels.modelMaker();
            }
            makeMedicalrecordsModels(root);
        }
        arrayListMedicalrecords.add(element);
    }

    public void deleteOneMedicalRecord(String firstLastName) {
        if (arrayListMedicalrecords.isEmpty()) {
            if (root == null){
                root = makingModels.modelMaker();
            }
            makeMedicalrecordsModels(root);
        }
        for (MedicalrecordsModel element : arrayListMedicalrecords) {
            if ((element.getFirstName() + " " + element.getLastName()).equals(firstLastName)) {
                arrayListMedicalrecords.remove(element);
            }
        }

    }
    // mettre à jour un dossier médical existant (comme évoqué précédemment, supposer que le
    //prénom et le nom de famille ne changent pas) ;


    public void updateAllergiesOrMeds(String firstLastName, String field, String action, String newAllergyOrMed) {
        if (arrayListMedicalrecords.isEmpty()) {
            if (root == null){
                root = makingModels.modelMaker();
            }
            makeMedicalrecordsModels(root);
        }
        for (MedicalrecordsModel element : arrayListMedicalrecords) {
            if ((element.getFirstName() +" "+ element.getLastName()).equals(firstLastName)) {
                if (field.equals("allergies")) {
                    ArrayList<String> listOfAllergies = element.getAllergies();
                    if (action.equals("add")) {

                        System.out.println(listOfAllergies);
                        listOfAllergies.add(newAllergyOrMed);
                    }
                    if (action.equals("delete")) {
                        listOfAllergies = element.getAllergies();
                        listOfAllergies.remove(newAllergyOrMed);
                    }
                    element.setAllergies(listOfAllergies);
                }
                if (field.equals("medications"))    {
                    HashMap<String, String> mapOfMedications = element.getMedications();
                    if (action.equals("add")) {
                        int index = newAllergyOrMed.indexOf("=");
                        String medName = newAllergyOrMed.substring(0, index);
                        String medQuantity = newAllergyOrMed.substring(index + 1);
                        if (mapOfMedications.containsKey(medName)) {
                            mapOfMedications.remove(medName);
                        }
                        mapOfMedications.put(medName, medQuantity);
                    }

                    if(action.equals("delete")){
                        mapOfMedications.remove(newAllergyOrMed);

                    }
                    element.setMedications(mapOfMedications);
                }
            }

        }

    }
    public int howOldIsThisPerson2(String stringDateOfBirth){
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        System.out.println("formatter ok");

        LocalDate dateOfBirth = LocalDate.parse(stringDateOfBirth, formatter);
        int age = Period.between(dateOfBirth, today).getYears();
        System.out.println("age"+ age);
        return age;
    }


   /* public int howOldIsThisPerson(String firstLastName, ArrayList <MedicalrecordsModel> list ){
        try{
            System.out.println("in function how old");
            System.out.println("firstlastName = "+ firstLastName);
            System.out.println(list);
            System.out.println("list printed");
            for (MedicalrecordsModel model : list) {
                System.out.println(model.toString());
                String modelsName = model.getFirstName()+" "+model.getLastName();
                if (firstLastName.equals(modelsName)) {
                    LocalDate today = LocalDate.now();
                    System.out.println("today"+today);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    System.out.println("formatter ok");
                    String stringDateOfBirth = model.getBirthdate();
                    System.out.println("stringdateofBirth" +stringDateOfBirth);


                    LocalDate dateOfBirth = LocalDate.parse(stringDateOfBirth, formatter);
                    System.out.println("dateOfBirth"+ dateOfBirth);
                    //LocalDate nineteenthBirthday = dateOfBirth.plusYears(19);
                    int age = Period.between(dateOfBirth, today).getYears();
                    System.out.println("age"+ age);
                    return age;
                }
            }
        }catch(Exception e){
            throw new RuntimeException("not found person");
        }
        return -1;
    }*/



}

