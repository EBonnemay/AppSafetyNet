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

       ListOfPersonsCoveredByAFirestationUrl1 result = new ListOfPersonsCoveredByAFirestationUrl1();
       try{
           result = urlService.urlOne(stationNumber);
           logger.info("successful calling of url 1");
       }catch(RuntimeException e){
           logger.error("unsuccessful calling of url 1");
       }

       return result;
   }


    @GetMapping("/childAlert")

    public ListOfChildrenAndTheHouseholdWithOneAddressUrl2 urlTwo(String address) {
        ListOfChildrenAndTheHouseholdWithOneAddressUrl2 result = new ListOfChildrenAndTheHouseholdWithOneAddressUrl2();
        try{
            result = urlService.urlTwo(address);
            logger.info("successful calling of url 2");
        }catch(RuntimeException e){
            logger.error("unsuccessful calling of url 2");
        }
        return result;
    }

    @GetMapping("/phoneAlert")
    public HashMap<String, ArrayList<String>> urlThree(@RequestParam String firestation) {
        HashMap<String, ArrayList<String>> result = new HashMap<>();
        try {
            result = urlService.urlThree(firestation);
            logger.info("successful calling of url 3");
        } catch (RuntimeException e) {
            logger.error("unsuccessful calling of url 3");
        }
        return result;
    }

    @GetMapping("/fire")
    public HouseholdUrl4 urlFour(String address) {
        HouseholdUrl4 result = new HouseholdUrl4();
        try{
            result = urlService.urlFour(address);
            logger.info("successful calling of url 4");
        }catch(RuntimeException e){
            logger.error("unsuccessful calling of url 4");
        }
        return result;
    }

    @GetMapping("/flood/stations")
    ListOfHouseholdsCoveredByAFirestationUrl5 urlFive(String stations) {
        ListOfHouseholdsCoveredByAFirestationUrl5 result = new ListOfHouseholdsCoveredByAFirestationUrl5();
        try{
            result = urlService.urlFive(stations);
            logger.info("successful calling of url 5");
        }catch(RuntimeException e){
            logger.error("unsuccessful calling of url 5");
        }
        return result;
    }


    @GetMapping("/personInfo")
    ListOfPersonsInfosUrl6 urlSix(String firstName, String lastName){
       ListOfPersonsInfosUrl6 result = new ListOfPersonsInfosUrl6();
        try{
            result = urlService.urlSix(firstName, lastName);
            logger.info("successful calling of url 6");
        }catch(RuntimeException e){
            logger.error("unsuccessful calling of url 6");
        }
        return result;
    }


    @GetMapping("/communityEmail")
    ArrayList<String> urlSeven(String city) {
        ArrayList<String> result = new ArrayList<>();
        try{
            result = urlService.urlSeven(city);
            logger.info("successful calling of url 7");
        }catch(RuntimeException e){
            logger.error("unsuccessful calling of url 7");
        }
        return result;

    }
}


