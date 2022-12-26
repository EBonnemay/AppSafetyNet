package com.safetynet.appSafetynet.controller;


import com.safetynet.appSafetynet.model.FirestationModel;
import com.safetynet.appSafetynet.service.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirestationController {
    @Autowired
    private FirestationService firestationService;


    @GetMapping("/firestations")
    public Iterable<FirestationModel> getFirestations() {
        return firestationService.getFirestations();
    }

    @PutMapping("/firestations")
    public void updateFirestation(@RequestParam String address, @RequestParam String number){
        firestationService.updateF( address, number);
        //que renvoie-t-il? 200? mais si après ça je fais un get mapping il va reprendre la liste initiale...

    }
}

