package com.safetynet.appSafetynet.repository;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.safetynet.appSafetynet.model.FirestationModel;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository

public class FirestationRepository {

    //ATTENTION PEU PERTINENT
//
    ArrayList<FirestationModel> arrayList = new ArrayList<>();
    public FirestationRepository() throws FileNotFoundException {
    }




    public Iterable<FirestationModel> findAll() {

        try {
            // Use JSONiter to parse the JSON file
            File file = ResourceUtils.getFile("classpath:data.json");
            System.out.println("file created");
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
            BufferedReader br = new BufferedReader(reader);
            //Stream stream = br.lines();
            String fileContent = br.lines().collect(Collectors.joining("\n"));
            System.out.println("string content created");
            Any root = JsonIterator.deserialize(fileContent);
            Any json_firestations = root.get("firestations");
            List<Any> list = json_firestations.asList();
            System.out.println(list);
            for (int i = 0; i < list.size(); i++) {

                //voir ce que fait jsoniter bindapi

                //FirestationModel firestationOject = new FirestationModel();
                FirestationModel model = new FirestationModel();
                model.setAddress(list.get(i).get("address").toString());
                model.setStation(list.get(i).get("station").toString());
                System.out.println(model);

                arrayList.add(model);

            }
            return arrayList;
            } catch(IOException e){
                System.out.println("IO exeption");
                return null;
            }
        }
    public String findStationServingOneAddress(String address){
        findAll();
        for (FirestationModel element : arrayList){
            if (element.getAddress().equals(address)){
                return(element.getStation());
            }
        }
        return null;
        //trouver la station correspondant à une adresse
    }
    public ArrayList<String> findAddressesServedByOneStation(String station){
        ArrayList<String> listOfAddressesServedByOneStation = new ArrayList<>();
        findAll();
        for (FirestationModel element : arrayList) {
            if (element.getStation().equals(station)) {
                listOfAddressesServedByOneStation.add(element.getAddress());
            }
        }
        return listOfAddressesServedByOneStation;
        //trouver les adresses desservies par une station
    }
    public void deleteOneAddressStationMapping(String address){
        findAll();
        for (FirestationModel element : arrayList) {
            if (element.getAddress().equals(address)) {
                arrayList.remove(element);
            }
        }
    }
    public void addOneAddressStationMapping(FirestationModel element){
            findAll();
            arrayList.add(element);
            //est ce pertinent de faire un bean de firestation model?
        //pourquoi firestationModel ne voit pas le bean?

        //ajouter un objet firemodel à une liste de firemodels

    }
    public void updateFirestationNumberForAddress(String address, String number){
        findAll();
        for (FirestationModel element : arrayList) {
            if (element.getAddress().equals(address)) {
                element.setStation(number);
                System.out.println("address "+ address + " has now firestation number "+ number);
                System.out.println(arrayList);
            }
        }
    }






}

