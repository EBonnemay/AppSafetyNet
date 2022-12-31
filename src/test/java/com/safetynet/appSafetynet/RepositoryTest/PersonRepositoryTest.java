package com.safetynet.appSafetynet.RepositoryTest;

import com.safetynet.appSafetynet.repository.PersonRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class PersonRepositoryTest {
    private static PersonRepository personRepository;


    @BeforeAll
    //désérialiser le fichier data
    public static void setUp() throws FileNotFoundException {
        personRepository= new PersonRepository();
    }


    @Tag("")
    @DisplayName("")

    @Test
    public void makePersonModelsTest(){
//ARRANGE
        // un fichier désérialisé en attribut de classe
        //
        // vérifier que personModels n'est plus vide après passage de la méthode
//ACT
        //personRepository.makePersonModels(Any deseralizedFile)
// ASSERT  l'attribut arrayList n'est pas vide
    }


    @Test
    public void findAllPersonsTest(){
        //FAUT IL APPELER LA METHODE MAKEPERSONMODELTESTS DANS LES AUTRES?
        //ArrayList persons remplie en attribut de classe
        //étant donnée une arrayList remplie de personModels, est ce que arrayList retourné par la méthode = arraylist attribut de classe?
    }
    @Test
    public void deletePersonTest(){
//ARRANGE
        //ArrayList persons remplie en attribut de classe
//ACT
//ASSERT
    }
    @Test
    public void addPersonTest() {
//ARRANGE
        //ArrayList persons remplie en attribut de classe
//ACT
//ASSERT

    }
    @Test
    public void updatePersonTest(){
//ARRANGE
//ACT
//ASSERT
    }
    @Test
    public void getPeopleInSameHouseholdTest(){
//ARRANGE
//ACT
//ASSERT
    }
    @Test
    public void howOldIsThisPerson2Test(){
//ARRANGE
        String dateOfBirth = "10/31/2017";
        int Expected  = 5;
        //ACT //ASSERT


    }
}
