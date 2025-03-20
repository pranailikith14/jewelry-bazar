package com.jewellery.compare.service;

import com.jewellery.compare.model.Person;
import com.jewellery.compare.repository.PersonRepository;
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
