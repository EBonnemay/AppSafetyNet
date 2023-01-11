package com.safetynet.appSafetynet.controller;


import com.safetynet.appSafetynet.model.FirestationModel;
import com.safetynet.appSafetynet.model.ListOfFirestationModels;
import com.safetynet.appSafetynet.service.FirestationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class FirestationController {
    @Autowired
    private FirestationService firestationService;
    static final Logger logger = LogManager.getLogger();

    @GetMapping("/firestations")
    public ListOfFirestationModels getFirestations() {

            return firestationService.getFirestations();

    }

    @PutMapping("/firestation")
    public void updateFirestation(@RequestBody FirestationModel model) throws Exception {
        firestationService.updateFirestation(model);
        logger.info("Firestation updated successfully");

    }
    @PostMapping("/firestation")
    public void addFirestation(@RequestParam FirestationModel model){
        firestationService.addFirestation(model);
        logger.info("Firestation added successfully");
    }

    @DeleteMapping("/firestation")
    public void deleteFirestation(@RequestParam String address){
        firestationService.deleteFirestation(address);
        logger.info("Firestation deleted successfully");
    }
}

