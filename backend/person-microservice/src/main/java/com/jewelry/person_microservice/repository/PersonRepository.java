package com.jewelry.person_microservice.repository;

import com.jewelry.person_microservice.entity.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PersonRepository extends MongoRepository<Person, String > {
    List<Person> findByCity(String city);
}
