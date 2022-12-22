package com.safetynet.appSafetynet.controller;

import com.safetynet.appSafetynet.service.MedicalrecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MedicalrecordsController {
    @Autowired
    private MedicalrecordsService medicalrecordsService;
/*

    @GetMapping("/medicalrecords")
    public Iterable<Medicalrecords> getAllMedicalrecords() {
        return medicalrecordsService.getAllMedicalrecords();
    }
*/

}
