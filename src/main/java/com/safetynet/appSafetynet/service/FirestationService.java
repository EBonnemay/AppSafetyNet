package com.safetynet.appSafetynet.service;

import com.safetynet.appSafetynet.model.FirestationModel;
import com.safetynet.appSafetynet.repository.FirestationRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class FirestationService {

    @Autowired
    private FirestationRepository firestationRepository;
   // @Autowired
   // private FirestationModel firestation;
    /*public Optional<Firestation> getFirestation(final Long id) {
        return firestationRepository.findById(id);
    }*/

    public Iterable<FirestationModel> getFirestations() {
        return firestationRepository.findAll();
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
