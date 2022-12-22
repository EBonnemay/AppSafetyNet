package com.safetynet.appSafetynet.controller;


import com.safetynet.appSafetynet.model.FirestationModel;
import com.safetynet.appSafetynet.service.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirestationController {
    @Autowired
    private FirestationService firestationService;


    @GetMapping("/firestations")
    public Iterable<FirestationModel> getFirestations() {
        return firestationService.getFirestations();
    }
}

