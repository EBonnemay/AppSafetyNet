package com.safetynet.appSafetynet.repository;
import com.safetynet.appSafetynet.model.Firestation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.safetynet.appSafetynet.model.Person;
@Repository
public interface PersonRepository extends CrudRepository<Person, Long>{
}
