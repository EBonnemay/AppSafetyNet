package com.safetynet.appSafetynet.repository;

import com.jsoniter.any.Any;
import com.jsoniter.spi.JsonException;
import com.safetynet.appSafetynet.model.FirestationModel;
import com.safetynet.appSafetynet.model.ListOfFirestationModels;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Data
@Repository
public class FirestationRepository implements IFirestationRepository{
    @Autowired

    MakingModels makingModels;
    ListOfFirestationModels listOfFirestationModels = new ListOfFirestationModels(); //1 object returned by the call

    Any root;

    static final Logger logger = LogManager.getLogger();

    public FirestationRepository() {
    }
    @Override
    public ListOfFirestationModels fillInFirestationModels(Any deserializedFile) {

        List<FirestationModel> attributeList = new ArrayList<>();
        Any json_firestations;

        try {
            json_firestations = deserializedFile.get("firestations");
            List<Any> list = json_firestations.asList();
            for (int i = 0; i < list.size(); i++) {
                FirestationModel model = new FirestationModel();
                model.setAddress(list.get(i).get("address").toString());
                model.setStation(list.get(i).get("station").toString());
                attributeList.add(model);
            }

        }catch(JsonException e){
            logger.error("the key 'firestation' was not found in deserialized file");
            throw new JsonException("key not found", e);

        }
        listOfFirestationModels.setListOfFirestationModels(attributeList);
        return listOfFirestationModels;
    }

    @Override
    public void setUpListOfFirestationsModel(){
        if (listOfFirestationModels.getListOfFirestationModels().size()==0) {
            if (root == null) {
                root = makingModels.modelMaker("classpath:data.json");
            }
            listOfFirestationModels = fillInFirestationModels(root);
        }
    }
    @Override
    public ListOfFirestationModels findAll() {

        setUpListOfFirestationsModel();
        return listOfFirestationModels;
    }

    @Override
    public ArrayList <String> findAddressesServedByOneStation(String numberOfStation, ListOfFirestationModels listOfFirestationModels){
        int match = 0;
        setUpListOfFirestationsModel();
        ArrayList<String> listOfAddressesServedByOneStation = new ArrayList<>();
        for (FirestationModel element : listOfFirestationModels.getListOfFirestationModels()){
            if(numberOfStation.equals(element.getStation())&&!listOfAddressesServedByOneStation.contains(element.getAddress())){ //number0fStation est NULL
                match = 1;
                listOfAddressesServedByOneStation.add(element.getAddress());
            }
        }
        if( match == 0){
            logger.error("Unsuccessful calling of url : the firestation number required is not in the data.");
            throw new RuntimeException("firestation number not found");
        }
        return listOfAddressesServedByOneStation;
    }
    @Override
    public ListOfFirestationModels deleteFirestation(FirestationModel model){
        setUpListOfFirestationsModel();
        int match = 0;
        for (int i=0;i<listOfFirestationModels.getListOfFirestationModels().size();i++) {
            if (listOfFirestationModels.getListOfFirestationModels().get(i).equals(model)) {
                match = 1;
                listOfFirestationModels.getListOfFirestationModels().remove(listOfFirestationModels.getListOfFirestationModels().get(i));
                i--;
            }
        }
        if(match==0){
            logger.error("the key address of this firestation Model does not exist in data ; no deleting");
            throw new RuntimeException("no deleting");
        }
        return listOfFirestationModels;

    }

    @Override
    public ListOfFirestationModels addOneAddressStationMapping(FirestationModel model){
        setUpListOfFirestationsModel();
        if(!listOfFirestationModels.getListOfFirestationModels().contains(model)){
            listOfFirestationModels.getListOfFirestationModels().add(model);
        }
        else{
            logger.error("the firestation to add is already in the data. No adding");
            throw new RuntimeException("no adding");
        }
        return listOfFirestationModels;
    }
    @Override
    public FirestationModel updateFirestationNumberForAnAddress(FirestationModel updatedFirestationModel)throws RuntimeException{
        setUpListOfFirestationsModel();
        int match = 0;
        FirestationModel result = new FirestationModel();
        for(int i=0;i<listOfFirestationModels.getListOfFirestationModels().size();i++){
            if(listOfFirestationModels.getListOfFirestationModels().get(i).getAddress().equals(updatedFirestationModel.getAddress())){
                match = 1;
                listOfFirestationModels.getListOfFirestationModels().set(i, updatedFirestationModel);
                result = listOfFirestationModels.getListOfFirestationModels().get(i);

            }

        }
        if(match == 0){
            logger.error("key address of the firestation Model not found in the data");
            throw new RuntimeException("no updating");

        }
       return result;
    }
}

