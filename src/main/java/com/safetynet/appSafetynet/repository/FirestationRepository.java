package com.safetynet.appSafetynet.repository;

import com.jsoniter.any.Any;
import com.safetynet.appSafetynet.model.FirestationModel;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
@Data
@Repository
public class FirestationRepository {
    @Autowired
   MakingModels makingModels;
    ArrayList<FirestationModel> arrayListFirestations = new ArrayList<>();

    Any root;
    public FirestationRepository() throws FileNotFoundException {
    }
    public void makeFirestationModels(Any deserializedFile){
        try {
            //Any deserializedFile = makingModels.modelMaker();

            Any json_firestations = deserializedFile.get("firestations");
            List<Any> list = json_firestations.asList();

            for (int i = 0; i < list.size(); i++) {
                FirestationModel model = new FirestationModel();
                model.setAddress(list.get(i).get("address").toString());
                model.setStation(list.get(i).get("station").toString());

                arrayListFirestations.add(model);
            }
        } catch(Exception e){
            System.out.println("problems filling models");
        }
    }
    public Iterable<FirestationModel> findAll() {
        //arrayListFirestations.clear();
        if (arrayListFirestations.isEmpty()){
            if(root == null){
                root = makingModels.modelMaker();
            }
            makeFirestationModels(root);
        }
        return arrayListFirestations;
    }
    public String findStationServingOneAddress(String address){
        if (arrayListFirestations.isEmpty()){
            if(root == null){
                root = makingModels.modelMaker();
            }
            makeFirestationModels(root);
        }
        for (FirestationModel element : arrayListFirestations){
            if (element.getAddress().equals(address)){
                return(element.getStation());
            }
        }
        return null;
    }
    public ArrayList <String> findAddressesServedByOneStation(String numberOfStation, ArrayList<FirestationModel> array){
        //if(root==null){
        //root = makingModels.modelMaker();
        //}
        //makeFirestationModels(root);
        ArrayList<String> listOfAddressesServedByOneStation = new ArrayList<>();
        for (FirestationModel element : array){
            if(numberOfStation.equals(element.getStation())&&!listOfAddressesServedByOneStation.contains(element.getAddress())){ //number0fStation est NULL
                listOfAddressesServedByOneStation.add(element.getAddress());
            }
        }
        return listOfAddressesServedByOneStation;
    }
    public void deleteOneAddressStationMapping(String address){
        if (arrayListFirestations.isEmpty()){
            if(root == null){
                root = makingModels.modelMaker();
            }
            makeFirestationModels(root);
        }
        for (FirestationModel element : arrayListFirestations) {
            if (element.getAddress().equals(address)) {
                arrayListFirestations.remove(element);
            }
        }
    }
    public void addOneAddressStationMapping(FirestationModel element){
        if (arrayListFirestations.isEmpty()){
            if(root == null){
                root = makingModels.modelMaker();
            }
            makeFirestationModels(root);
        }
            arrayListFirestations.add(element);


    }
    public void updateFirestationNumberForAddress(String address, String number){
        if (arrayListFirestations.isEmpty()){
            if(root == null){
                root = makingModels.modelMaker();
            }
            makeFirestationModels(root);
        }
        for (FirestationModel element : arrayListFirestations) {
            if (element.getAddress().equals(address)) {
                element.setStation(number);
                System.out.println("address "+ address + " has now firestation number "+ number);
                System.out.println(arrayListFirestations);
            }
        }
    }
}

