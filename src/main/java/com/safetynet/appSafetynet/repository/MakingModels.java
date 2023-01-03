package com.safetynet.appSafetynet.repository;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.stream.Collectors;

@Repository

public class MakingModels {
    
    public Any modelMaker(){
        Any root = null;
        try {
            // Use JSONiter to parse the JSON file

            File file = ResourceUtils.getFile("classpath:data.json");
            System.out.println("file created");
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
            System.out.println("reader created)");
            BufferedReader br = new BufferedReader(reader);
            System.out.println("buffered reader created");
            String fileContent = br.lines().collect(Collectors.joining("\n"));
            System.out.println("string content created");
            root = JsonIterator.deserialize(fileContent);
            if(root == null){
                System.out.println("root is null");
            }
            System.out.println("root created");
        } catch(IOException e) {
            System.out.println("IO exeption");

        }
    return root;
    }
}
