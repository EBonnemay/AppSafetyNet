package com.safetynet.appSafetynet.repository;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.jsoniter.spi.JsonException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.stream.Collectors;

@Repository
public class MakingModels implements IMakingModels{
    static final Logger logger = LogManager.getLogger();

    @Override
    public Any modelMaker()  {
        Any root = null;

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
    return root;
    }
}
