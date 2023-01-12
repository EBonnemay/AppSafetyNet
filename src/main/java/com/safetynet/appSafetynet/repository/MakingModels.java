package com.safetynet.appSafetynet.repository;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.jsoniter.spi.JsonException;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.stream.Collectors;

@Repository
public class MakingModels implements IMakingModels{

    @Override
    public Any modelMaker(String ressource)  {
        Any root = null;

        BufferedReader br;
        String fileContent;
        try {
            File file = ResourceUtils.getFile(ressource);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
            br = new BufferedReader(reader);
            fileContent = br.lines().collect(Collectors.joining("\n"));
            root = JsonIterator.deserialize(fileContent);
        }catch(FileNotFoundException e){
            throw new RuntimeException("file not found", e);
        } catch(JsonException e) {
            throw new RuntimeException("the file could not be read", e);
        }catch(Exception e){
            System.out.println("modelMaker failed");
        }
        return root;
    }
}
