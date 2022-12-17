package com.safetynet.appSafetynet.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.safetynet.appSafetynet.model.Medicalrecords;
@Repository
public interface MedicalrecordsRepository extends CrudRepository<Medicalrecords, Long>{
}
