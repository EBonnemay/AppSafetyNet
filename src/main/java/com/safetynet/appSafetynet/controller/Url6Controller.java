package com.safetynet.appSafetynet.controller;

import com.safetynet.appSafetynet.service.UrlService6;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController

public class Url6Controller {
    @Autowired
    private UrlService6 urlService6;
    @GetMapping("/personInfo")
    HashMap<String, HashMap> urlSix(String firstName, String lastName) {
        return urlService6.urlSix(firstName, lastName);
    }
}
