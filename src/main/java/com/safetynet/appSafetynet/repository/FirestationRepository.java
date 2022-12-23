package com.safetynet.appSafetynet.repository;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.safetynet.appSafetynet.model.FirestationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository

public class FirestationRepository implements CrudRepository {

    //@Autowired
    //private EntityManager entityManager;

    @Autowired
    FirestationModel firestationObject;

    public FirestationRepository() throws FileNotFoundException {
    }

    @Override
    public Iterable saveAll(Iterable entities) {
        return null;
    }

    @Override
    public Object save(Object entity) {
        return null;
    }

    @Override
    public Optional findById(Object o) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Object o) {
        return false;
    }

    /*@Override
    public Iterable findAll() {
        return null;
    }*/

    @Override
    public Iterable findAllById(Iterable iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Object o) {

    }

    @Override
    public void delete(Object entity) {

    }

    @Override
    public void deleteAll(Iterable entities) {

    }

    @Override
    public void deleteAll() {

    }

    /*@Override
    public String value() {
        return "";
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }*/

   /* public void JSONToJavaObject() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper om = new ObjectMapper();
        File jsonFile = new File("classpath:data.json");
        FirestationModel fm = om.readValue(jsonFile, FirestationModel.class);
        System.out.println(fm.getAddress());
        System.out.println(fm.getStation());

    */


    public Iterable<FirestationModel> findAll() {
        ArrayList<FirestationModel> arrayList = new ArrayList<>();
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

                FirestationModel firestationObject = new FirestationModel();

                firestationObject.setAddress(list.get(i).get("address").toString());
                firestationObject.setStation(list.get(i).get("station").toString());
                System.out.println(firestationObject);

                arrayList.add(firestationObject);

            }
            return arrayList;
            } catch(IOException e){
                System.out.println("IO exeption");
                return null;
            }
        }
    }

