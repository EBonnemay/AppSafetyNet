package com.safetynet.appSafetynet.model.dto;

import lombok.Data;

import java.util.List;
@Data
public class ChildAndHisHouseholdForUrl2 {
    private String firstNameOfChild;
    private String lastNameOfChild;
    private int age;
    //private String address;(c'est le paramètre)
    private List <String> listOfOtherMembers;
}
