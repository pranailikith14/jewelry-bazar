package com.jewelry.person_microservice.controller;

import com.jewelry.person_microservice.entity.Person;
import com.jewelry.person_microservice.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/savePerson")
    public ResponseEntity<Map<String, Object>> savePerson(@RequestBody Person person) {
        personService.savePerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of("status", HttpStatus.CREATED.value(), "message", "User inserted successfully")
        );
    }

    @GetMapping("/getPersonByCity")
    public ResponseEntity<Map<String, Object>> getPersonByCity(@RequestParam String city) {
        List<Person> persons = personService.getPersonByCity(city);
        if (persons.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", HttpStatus.NOT_FOUND.value(), "message", "No users found in the specified city")
            );
        }
        return ResponseEntity.ok(
                Map.of("status", HttpStatus.OK.value(), "data", persons)
        );
    }
}
