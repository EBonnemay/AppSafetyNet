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
    public void setUpListOfFirestationsModel(){
        if (listOfFirestationModels.getListOfFirestationModels().size()==0) {
            if (root == null) {
                System.out.println("root is null");
                root = makingModels.modelMaker();
            }
            listOfFirestationModels = fillInFirestationModels(root);
            System.out.println("listOfFirestationModels filled in");
        }
    }

    public ListOfFirestationModels findAll() {

        setUpListOfFirestationsModel();
        return listOfFirestationModels;
    }

    public ArrayList <String> findAddressesServedByOneStation(String numberOfStation, ListOfFirestationModels listOfFirestationModels){
        setUpListOfFirestationsModel();
        ArrayList<String> listOfAddressesServedByOneStation = new ArrayList<>();
        for (FirestationModel element : listOfFirestationModels.getListOfFirestationModels()){
            if(numberOfStation.equals(element.getStation())&&!listOfAddressesServedByOneStation.contains(element.getAddress())){ //number0fStation est NULL
                listOfAddressesServedByOneStation.add(element.getAddress());
            }
        }
        return listOfAddressesServedByOneStation;
    }
    public void deleteOneAddressStationMapping(String address){
        setUpListOfFirestationsModel();
        List <FirestationModel> theList = listOfFirestationModels.getListOfFirestationModels();
        for (int i=0;i<listOfFirestationModels.getListOfFirestationModels().size();i++) {
            if (listOfFirestationModels.getListOfFirestationModels().get(i).getAddress().equals(address)) {
                theList.remove(listOfFirestationModels.getListOfFirestationModels().get(i));
                i--;
            }
        }
    }
    public void addOneAddressStationMapping(String address, String station){
        setUpListOfFirestationsModel();
        FirestationModel firestationModel = new FirestationModel();
        firestationModel.setStation(station);
        firestationModel.setAddress(address);
        List <FirestationModel> attributeList = listOfFirestationModels.getListOfFirestationModels();
        attributeList.add(firestationModel);
        listOfFirestationModels.setListOfFirestationModels(attributeList);



    }

    //put
    public void updateFirestationNumberForAnAddress(String address, String number){
        setUpListOfFirestationsModel();
        for (FirestationModel element : listOfFirestationModels.getListOfFirestationModels()) {
            if (element.getAddress().equals(address)) {
                element.setStation(number);
                System.out.println("address "+ address + " has now firestation number "+ number);
            }
        }
    }
}

