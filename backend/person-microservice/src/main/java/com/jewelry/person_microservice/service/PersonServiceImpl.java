package com.jewelry.person_microservice.service;

import com.jewelry.person_microservice.entity.Person;
import com.jewelry.person_microservice.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public List<Person> getPersonByCity(String city) {
        return personRepository.findByCity(city);
    }
}
