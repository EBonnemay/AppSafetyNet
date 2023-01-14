package com.safetynet.appSafetynet.controller;


import com.safetynet.appSafetynet.model.dto.*;
import com.safetynet.appSafetynet.service.UrlService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class UrlController {
    @Autowired
    private UrlService urlService;

    static final Logger logger = LogManager.getLogger();
   @GetMapping("/firestation")
    public ListOfPersonsCoveredByAFirestationUrl1 urlOne(@RequestParam String stationNumber) {

       //return urlService.urlOne(stationNumber);
       ListOfPersonsCoveredByAFirestationUrl1 result;
       result = urlService.urlOne(stationNumber);
       logger.info("successful calling of url 1");
       return result;
   }


    @GetMapping("/childAlert")

    public ListOfChildrenAndTheHouseholdWithOneAddressUrl2 urlTwo(String address) {
        ListOfChildrenAndTheHouseholdWithOneAddressUrl2 result;
        result = urlService.urlTwo(address);
        logger.info("successful calling of url 2");
        return result;

    }

    @GetMapping("/phoneAlert")
    public HashMap<String, ArrayList<String>> urlThree(@RequestParam String firestation) {
        HashMap<String, ArrayList<String>> result =  urlService.urlThree(firestation);
        logger.info("successful calling of url 2");
       return result;
    }

    @GetMapping("/fire")
    public HouseholdUrl4 urlFour(String address) {
        HouseholdUrl4 result = urlService.urlFour(address);
        logger.info("successful calling of url4");
        return result;
    }

    @GetMapping("/flood/stations")
    ListOfHouseholdsCoveredByAFirestationUrl5 urlFive(String stations) {
        ListOfHouseholdsCoveredByAFirestationUrl5 result;
        result = urlService.urlFive(stations);
        logger.info("successful calling of url 5");
        return result;
    }


    @GetMapping("/personInfo")
    ListOfPersonsInfosUrl6 urlSix(String firstName, String lastName){
       ListOfPersonsInfosUrl6 result = urlService.urlSix(firstName, lastName);
        logger.info("successful calling of url 6");
        return result;
    }


    @GetMapping("/communityEmail")
    ArrayList<String> urlSeven(String city) {
        ArrayList<String> result = urlService.urlSeven(city);
        logger.info("successful calling of url 7");
           return result;

    }
}


