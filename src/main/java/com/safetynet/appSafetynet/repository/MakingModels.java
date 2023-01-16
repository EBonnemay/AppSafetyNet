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
            logger.error("non existing file");
            throw new RuntimeException("file not found", e);
        } catch(JsonException e) {
            logger.error("impossible to read file");
            throw new RuntimeException("the file could not be read", e);
        }
        return root;
    }
}
