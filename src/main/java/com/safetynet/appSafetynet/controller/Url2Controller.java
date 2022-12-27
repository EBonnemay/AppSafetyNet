package com.safetynet.appSafetynet.controller;


import com.safetynet.appSafetynet.service.UrlService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class Url2Controller {
    @Autowired
    private UrlService2 urlService2;


    @GetMapping("/childAlert")
    public HashMap<String, ArrayList> urlTwo(String address) {
        return urlService2.urlTwo(address);
    }
}