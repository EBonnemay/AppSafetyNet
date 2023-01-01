package com.safetynet.appSafetynet.controller;


import com.safetynet.appSafetynet.model.dto.*;
import com.safetynet.appSafetynet.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class UrlController {
    @Autowired
    private UrlService urlService;


    @GetMapping("/firestation")
    public ListOfPersonsCoveredByAFirestationUrl1 urlOne(String stationNumber) {
        System.out.println("firestationNumber in control " + stationNumber);
        return urlService.urlOne(stationNumber);
    }


    @GetMapping("/childAlert")

    public ListOfChildrenAndTheHouseholdWithOneAddressUrl2 urlTwo(String address) {

        System.out.println(address);
        return urlService.urlTwo(address);
    }

    @GetMapping("/phoneAlert")
    public HashMap<String, ArrayList<String>> urlThree(String firestation) {
        return urlService.urlThree(firestation);
    }

    @GetMapping("/fire")
    public HouseholdUrl4 urlFour(String address) {
        System.out.println("address in control " + address);
        return urlService.urlFour(address);
    }

    @GetMapping("/flood/stations")
    ListOfHouseholdsCoveredByAFirestationUrl5 urlFive(String stations) {
        return urlService.urlFive(stations);
    }


    @GetMapping("/personInfo")
    PersonInfoUrl6 urlSix(String firstName, String lastName) {
        return urlService.urlSix(firstName, lastName);
    }


    @GetMapping("/communityEmail")
    ArrayList<String> urlSeven(String city) {
        return urlService.urlSeven(city);
    }
}


