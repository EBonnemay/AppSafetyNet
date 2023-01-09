package com.safetynet.appSafetynet.repository;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.jsoniter.spi.JsonException;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.stream.Collectors;

@Repository

public class MakingModels {
//1. throws dans la signature de la fonction 1 depuis l'entrée du fichier jusqu'au rendu de l'objet buffered reader
//2. autre fonction dans même classe, deux try-catch : premier IO exception avec à l'intérieur un throw runtime
//3. puis dans cette fonction, if file is empty, print file empty
//4. deuxième try catch : try read line by line, deuxième catch IO exception et throw aussi une runtime
    public Any modelMaker()  {
        Any root = null;
        //try {
            // Use JSONiter to parse the JSON file
            //InputStreamReader reader;
            BufferedReader br;
            String fileContent;

            try {
                File file = ResourceUtils.getFile("classpath:data.json");
                InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
                br = new BufferedReader(reader);
                fileContent = br.lines().collect(Collectors.joining("\n"));
                root = JsonIterator.deserialize(fileContent);
            }catch(JsonException e) {
                throw new RuntimeException("the file could not be read", e);
            }catch(FileNotFoundException e){
                throw new RuntimeException("file not found, sorry", e);
            }catch(IOException e){
                throw new RuntimeException("the program go IOException",e);
            }catch(Exception e){
                System.out.println("exception throwed");
                //throw new RuntimeException("the file could not be deserialized",e);

            }

            //if(root == null){
                //System.out.println("root is null");
            //}
            //System.out.println("root created");
       // } catch(IOException e) {
            //System.out.println("IO exeption");
            //throw new IOException("File could not be deserialized", e);
                    //catch (IOException e) {
            //            throw new RuntimeException("file could not be opened", e);

        //}
    return root;
    }
}
