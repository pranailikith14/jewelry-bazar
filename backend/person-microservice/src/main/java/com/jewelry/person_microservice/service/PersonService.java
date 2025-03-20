package com.jewelry.person_microservice.service;

import com.jewelry.person_microservice.entity.Person;

import java.util.List;

public interface PersonService {

    /**
     *  This will save the person data to the mongo
     */
    Person savePerson(Person person);

    /**
     * This will get the person data based oon city
     */
    List<Person> getPersonByCity(String city);
}
