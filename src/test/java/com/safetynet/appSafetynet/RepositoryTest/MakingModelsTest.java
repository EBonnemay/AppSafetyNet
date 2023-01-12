package com.safetynet.appSafetynet.RepositoryTest;

import com.safetynet.appSafetynet.repository.MakingModels;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MakingModelsTest {
    @Autowired
    MakingModels makingModels;


    @Test
    public void modelMakerTest(){
        Assertions.assertNotEquals(null, makingModels.modelMaker("classpath:data.json"));
    }

    @Test
    public void modelMakerWithNonExistentFileTest(){
        Exception exception = Assertions.assertThrows(Exception.class, () -> makingModels.modelMaker("classpath:data4.json"));
        Assertions.assertEquals("file not found", exception.getMessage());
    }

    @Test
    public void modelMakerWithEmptyFileTest(){
        Exception exception = Assertions.assertThrows(Exception.class, () -> makingModels.modelMaker("classpath:data3.json"));
        Assertions.assertEquals("the file could not be read", exception.getMessage());
        //.assertThrows(RuntimeException.class, ()-> makingModels.modelMaker("classpath:data3.json"));
    }


}
