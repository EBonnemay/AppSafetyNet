package com.safetynet.appSafetynet.service;

import com.safetynet.appSafetynet.repository.MedicalrecordsRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class MedicalrecordsService {
    @Autowired
    private MedicalrecordsRepository medicalrecordsRepository;

    /*public Optional<Medicalrecords> getMedicalrecords(final Long id) {
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
*/
}
