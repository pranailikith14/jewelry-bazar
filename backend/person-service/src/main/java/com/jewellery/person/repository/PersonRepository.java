package com.jewellery.person.repository;

import com.jewellery.person.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PersonRepository extends MongoRepository<Person, String> {
    List<Person> findByCity(String city);
}
