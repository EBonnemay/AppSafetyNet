package com.safetynet.appSafetynet.repository;

import com.jsoniter.any.Any;
import com.safetynet.appSafetynet.model.FirestationModel;
import com.safetynet.appSafetynet.model.ListOfFirestationModels;
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
    ListOfFirestationModels listOfFirestationModels = new ListOfFirestationModels(); //1 object returned by the call

    Any root;
    public FirestationRepository() throws FileNotFoundException {
    }
    public ListOfFirestationModels fillInFirestationModels(Any deserializedFile){

        List<FirestationModel> attributeList = new ArrayList<>();
        try {
            //Any deserializedFile = makingModels.modelMaker();

            Any json_firestations = deserializedFile.get("firestations");
            List<Any> list = json_firestations.asList();

            for (int i = 0; i < list.size(); i++) {
                FirestationModel model = new FirestationModel();
                model.setAddress(list.get(i).get("address").toString());
                model.setStation(list.get(i).get("station").toString());
                //List <FirestationModel> listOfFirestations = listOfFirestationModels.getListOfFirestationModels();
                attributeList.add(model);
            }
            listOfFirestationModels.setListOfFirestationModels(attributeList);
        } catch(Exception e){
            System.out.println("problems filling models");
        }
        return listOfFirestationModels;
    }

    public ListOfFirestationModels findAll() {
        //arrayListFirestations.clear();

        if (listOfFirestationModels==null){
            if(root == null){
                root = makingModels.modelMaker();
            }
            fillInFirestationModels(root);
        }
        return listOfFirestationModels;
    }
    /*public String findStationServingOneAddress(String address){
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
    }*/
    public ArrayList <String> findAddressesServedByOneStation(String numberOfStation, ListOfFirestationModels listOfFirestationModels){
        //if(root==null){
        //root = makingModels.modelMaker();
        //}
        //makeFirestationModels(root);
        ArrayList<String> listOfAddressesServedByOneStation = new ArrayList<>();
        for (FirestationModel element : listOfFirestationModels.getListOfFirestationModels()){
            if(numberOfStation.equals(element.getStation())&&!listOfAddressesServedByOneStation.contains(element.getAddress())){ //number0fStation est NULL
                listOfAddressesServedByOneStation.add(element.getAddress());
            }
        }
        return listOfAddressesServedByOneStation;
    }
    public void deleteOneAddressStationMapping(String address){
        if (listOfFirestationModels==null){
            if(root == null){
                root = makingModels.modelMaker();
            }
            listOfFirestationModels = fillInFirestationModels(root);
        }
        for (FirestationModel element : listOfFirestationModels.getListOfFirestationModels()) {
            if (element.getAddress().equals(address)) {
                List <FirestationModel> theList = listOfFirestationModels.getListOfFirestationModels();
                theList.remove(element);
                listOfFirestationModels.setListOfFirestationModels(theList);
            }
        }
    }
    public void addOneAddressStationMapping(String address, String station){
        if (listOfFirestationModels==null){
            if(root == null){
                root = makingModels.modelMaker();
            }
            listOfFirestationModels = fillInFirestationModels(root);
        }
        FirestationModel firestationModel = new FirestationModel();
        firestationModel.setStation(station);
        firestationModel.setAddress(address);
        List <FirestationModel> attributeList = listOfFirestationModels.getListOfFirestationModels();
        attributeList.add(firestationModel);
        listOfFirestationModels.setListOfFirestationModels(attributeList);



    }
    /*public void deleteOneAddressStationMapping(FirestationModel element){
        if (arrayListFirestations.isEmpty()){
            if(root == null){
                root = makingModels.modelMaker();
            }
            makeFirestationModels(root);
        }
        arrayListFirestations.remove(element);
    }*/
    public void updateFirestationNumberForAddress(String address, String number){
        if (listOfFirestationModels==null){
            if(root == null){
                root = makingModels.modelMaker();
            }
            fillInFirestationModels(root);
        }
        for (FirestationModel element : listOfFirestationModels.getListOfFirestationModels()) {
            if (element.getAddress().equals(address)) {
                element.setStation(number);
                System.out.println("address "+ address + " has now firestation number "+ number);
            }
        }
    }
}

