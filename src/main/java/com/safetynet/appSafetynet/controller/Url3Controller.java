package com.safetynet.appSafetynet.controller;


import com.safetynet.appSafetynet.service.UrlService3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class Url3Controller {
    @Autowired
    private UrlService3 urlService3;


    @GetMapping("/phoneAlert")
    public HashMap<String, ArrayList<String>> urlThree(String firestation) {
        return urlService3.urlThree(firestation);
    }
}