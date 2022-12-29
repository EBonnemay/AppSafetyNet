package com.safetynet.appSafetynet.controller;


import com.safetynet.appSafetynet.service.UrlService1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class Url1Controller {
    @Autowired
    private UrlService1 urlService1;


    @GetMapping("/firestation")
    public HashMap<String, HashMap> urlOne(String stationNumber) {
        System.out.println("firestationNumber in control "+stationNumber);
        return urlService1.urlOne(stationNumber);
    }
}
