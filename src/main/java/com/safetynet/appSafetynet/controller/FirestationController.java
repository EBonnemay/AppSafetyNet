package com.safetynet.appSafetynet.controller;

import com.safetynet.appSafetynet.model.Firestation;
import com.safetynet.appSafetynet.service.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
public class FirestationController {
    @Autowired
    private FirestationService firestationService;

    /**
     * Read - Get all employees
     * @return - An Iterable object of Employee fulfilled
     */
    @GetMapping("/medicalrecords")
    public Iterable<Firestation> getFirestations() {
        return firestationService.getFirestations();
    }
}

