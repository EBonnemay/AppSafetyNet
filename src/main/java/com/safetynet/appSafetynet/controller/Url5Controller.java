package com.safetynet.appSafetynet.controller;


import com.safetynet.appSafetynet.service.UrlService5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class Url5Controller {

    @Autowired
    private UrlService5 urlService5;

    @GetMapping("/flood/stations")
HashMap<String, Object> urlFive(String stations) {
        return urlService5.urlFive(stations);
    }
}