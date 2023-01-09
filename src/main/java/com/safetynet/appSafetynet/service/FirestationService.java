package com.safetynet.appSafetynet.service;

import com.safetynet.appSafetynet.model.FirestationModel;
import com.safetynet.appSafetynet.model.ListOfFirestationModels;
import com.safetynet.appSafetynet.repository.FirestationRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class FirestationService {

    @Autowired
    private FirestationRepository firestationRepository;

    public ListOfFirestationModels getFirestations() {
        return firestationRepository.findAll();
    }
    public void updateFirestation(FirestationModel model){
        firestationRepository.updateFirestationNumberForAnAddress( model);
    }

    public void deleteFirestation(String address) {
        firestationRepository.deleteOneAddressStationMapping(address);
    }
    public void addFirestation(String address, String number){
        firestationRepository.addOneAddressStationMapping(address, number);
    }


}
