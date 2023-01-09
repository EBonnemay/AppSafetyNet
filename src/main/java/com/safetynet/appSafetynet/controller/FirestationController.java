package com.safetynet.appSafetynet.controller;


import com.safetynet.appSafetynet.model.FirestationModel;
import com.safetynet.appSafetynet.model.ListOfFirestationModels;
import com.safetynet.appSafetynet.service.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class FirestationController {
    @Autowired
    private FirestationService firestationService;


    @GetMapping("/firestation")
    public ListOfFirestationModels getFirestations() {
        return firestationService.getFirestations();
    }

    @PutMapping("/firestation")
    public void updateFirestation(@RequestBody FirestationModel model){
        firestationService.updateFirestation( model);


    }
    @PostMapping("/firestation")
    public void addFirestation(@RequestParam String address, @RequestParam String number){
        firestationService.addFirestation(address, number);
    }

    @DeleteMapping("/firestation")
    public void deleteFirestation(@RequestParam String address){
        firestationService.deleteFirestation(address);
    }
}

