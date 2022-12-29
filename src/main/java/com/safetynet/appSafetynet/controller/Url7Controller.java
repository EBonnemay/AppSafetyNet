package com.safetynet.appSafetynet.controller;

import com.safetynet.appSafetynet.service.UrlService7;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class Url7Controller {
    @Autowired
    private UrlService7 urlService7;
    @GetMapping("/communityEmail")
    ArrayList<String> urlSeven(String city) {
        return urlService7.urlSeven(city);
    }
}
