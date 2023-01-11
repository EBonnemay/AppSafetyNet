package com.safetynet.appSafetynet.service;

import com.safetynet.appSafetynet.model.FirestationModel;
import com.safetynet.appSafetynet.model.ListOfFirestationModels;
import com.safetynet.appSafetynet.repository.IFirestationRepository;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class FirestationService {
    static final Logger logger = LogManager.getLogger();

    @Autowired
    private IFirestationRepository firestationRepository;

    public ListOfFirestationModels getFirestations() {
        return firestationRepository.findAll();
    }
    public void updateFirestation(FirestationModel model){
        firestationRepository.updateFirestationNumberForAnAddress(model);

    }

    public void deleteFirestation(String address) {
        firestationRepository.deleteOneAddressStationMapping(address);

    }
    public void addFirestation(FirestationModel model){
        firestationRepository.addOneAddressStationMapping(model);


    }


}
