package com.safetynet.appSafetynet.service;
import java.util.Optional;

import com.safetynet.appSafetynet.model.Firestation;
import com.safetynet.appSafetynet.repository.FirestationRepository;
import com.safetynet.appSafetynet.repository.MedicalrecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.safetynet.appSafetynet.model.Medicalrecords;
import com.safetynet.appSafetynet.repository.MedicalrecordsRepository;

import lombok.Data;

@Data
@Service
public class MedicalrecordsService {
    @Autowired
private MedicalrecordsRepository medicalrecordsRepository;

    public Optional<Medicalrecords> getMedicalrecords(final Long id) {
        return medicalrecordsRepository.findById(id);
    }

    public Iterable<Medicalrecords> getAllMedicalrecords() {
        return medicalrecordsRepository.findAll();
    }

    public void deleteEmployee(final Long id) {
        medicalrecordsRepository.deleteById(id);
    }

    public Medicalrecords saveMedicalrecords(Medicalrecords medicalrecords) {
        Medicalrecords savedMedicalrecords = medicalrecordsRepository.save(medicalrecords);
        return savedMedicalrecords;
    }

}
