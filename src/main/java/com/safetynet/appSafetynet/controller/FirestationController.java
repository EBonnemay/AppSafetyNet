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
    public FirestationModel updateFirestation(@RequestBody FirestationModel model) {
        FirestationModel result = new FirestationModel();
        try{
            result = firestationService.updateFirestation(model);
            logger.info("firestation updated successfully");
            return result;
        }
        catch(RuntimeException e){
            logger.error("firestation updating failed");
            return result;
        }
    }
    @PostMapping("/firestation")
    public ListOfFirestationModels addFirestation(@RequestBody FirestationModel model) {
        ListOfFirestationModels result = new ListOfFirestationModels();
        try {
            result = firestationService.addFirestation(model);
            logger.info("firestation added successfully");
            return result;
        } catch (RuntimeException e) {
            logger.error("firestation adding failed");
            return result;
        }
    }
    @DeleteMapping("/firestation")
    public ListOfFirestationModels deleteFirestation(@RequestBody FirestationModel model){
            ListOfFirestationModels result = new ListOfFirestationModels();
            try {
                result = firestationService.deleteFirestation(model);
                logger.info("firestation updated successfully");
                return result;
            } catch (RuntimeException e) {
                logger.error("firestation deleting failed");
                return result;
            }
    }
}

