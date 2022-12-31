package com.safetynet.appSafetynet.service;

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
    public void updateF(String address, String number){
        firestationRepository.updateFirestationNumberForAddress( address,  number);
    }
    /*
    public void deleteFirestation(final Long id) {
        firestationRepository.deleteById(id);
    }

    public Firestation saveFirestation(Firestation firestation) {
        Firestation savedFirestation = firestationRepository.save(firestation);
        return savedFirestation;
    }*/

}
