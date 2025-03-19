package com.jewellery.compare.repository;

import com.jewellery.compare.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PersonRepository extends MongoRepository<Person, String> {
    List<Person> findByCity(String city);
}
