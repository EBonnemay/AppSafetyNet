package com.safetynet.appSafetynet.repository;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.safetynet.appSafetynet.model.FirestationModel;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository

public class FirestationRepository implements Repository {

    //@Autowired
    //private EntityManager entityManager;


    public FirestationRepository() throws FileNotFoundException {
    }

    @Override
    public String value() {
        return "";
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

    public Iterable<FirestationModel> findAll() {
        ArrayList<FirestationModel> arrayList = new ArrayList<>();
        try {
            // Use JSONiter to parse the JSON file
            File file = ResourceUtils.getFile("classpath:data.json");
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
            String fileContent = new BufferedReader(reader).lines().collect(Collectors.joining("\n"));
            Any root = JsonIterator.deserialize(fileContent);

            // Get the three lists from the JSON file
            Any json_firestations = root.get("firestations");
            List<Any> list = json_firestations.asList();

            // Iterate over the lists and create an object for each item in the second list
            for (int i = 0; i < list.size(); i++) {
                //voir ce que fait jsoniter bindapi
                FirestationModel firestationObject = new FirestationModel();
                firestationObject.setAddress(list.get(0).toString());
                firestationObject.setStation(list.get(1).toString());
                //ça reglera le probleme de .value qui sera nécessaire sinon

                // Save the object using the repository
                //save(firestationObject);
                arrayList.add(firestationObject);

            }
            return arrayList;
        } catch (IOException e) {
            System.out.println("IO exeption");
            return null;
        }
    }
}

    /*public ArrayList findAll()  {
        Reader reader;
        try {
            // Open the JSON file
            File file = ResourceUtils.getFile("classpath:data.json");
            //FileInputStream inputStream = new FileInputStream("src/main/resources/data.json");
            FileInputStream inputStream = new FileInputStream(file);
            reader = new InputStreamReader(inputStream);
        }
        catch (FileNotFoundException e) {
            return null;
        }

        // Create a JSONiter object to parse the file
        ArrayList<Any> list = new ArrayList<>();
        try {
            JsonIterator iter = JsonIterator.parse(String.valueOf(reader));
            // Read the top-level object in the JSON file
            Any root = iter.readAny();

            List<FirestationModel> firestations = root.get("firestations", FirestationModel[].class);
            // Iterate through the first list of objects
            for (Any element : root.get("firestations").asList()) {
                System.out.println(element);
                list.add(element);
            }
            return list;
        }
        catch(IOException e) {
            return null;
        }
    }
}
/*


@Repository
public class MyObjectRepository implements CrudRepository<MyObject, Long> {

    @Autowired
    private EntityManager entityManager;

    public void parseJsonFile(String jsonFilePath) {
        try {
            // Use JSONiter to parse the JSON file
            Any root = JsonIterator.deserialize(new FileInputStream(jsonFilePath));

            // Get the three lists from the JSON file
            List<Any> list1 = root.get("list1").asList();
            List<Any> list2 = root.get("list2").asList();
            List<Any> list3 = root.get("list3").asList();

            // Iterate over the lists and create an object for each item in the second list
            for (int i = 0; i < list2.size(); i++) {
                MyObject obj = new MyObject();
                obj.setField1(list1.get(i).toString());
                obj.setField2(list2.get(i).toString());
                obj.setField3(list3.get(i).toString());

                // Save the object using the repository
                save(obj);
            }
        } catch (IOException e) {
            // Handle exception
        }
    }

    // Other methods inherited from CrudRepository (e.g. save, findById, delete)
}
To use this service, you will need to have a MyObject (Fireclass that represents the object you want to create, and a MyObjectRepository interface that extends CrudRepository<MyObject, Long> to provide basic CRUD operations for the MyObject class.


*/