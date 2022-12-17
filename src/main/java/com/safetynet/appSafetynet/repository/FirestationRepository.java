package com.safetynet.appSafetynet.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.safetynet.appSafetynet.model.Firestation;
@Repository

public interface FirestationRepository extends CrudRepository<Firestation, Long>{


}
