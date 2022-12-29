package com.safetynet.appSafetynet.controller;

import com.safetynet.appSafetynet.service.UrlService4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class Url4Controller {


    @Autowired
    private UrlService4 urlService4;


    @GetMapping("/fire")
    public HashMap<String, Object> urlFour(String address) {
        System.out.println("address in control "+address);
        return urlService4.urlFour(address);
    }
}