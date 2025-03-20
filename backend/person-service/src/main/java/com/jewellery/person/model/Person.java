package com.jewellery.person.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "person_search")
public class Person {

    @Id
    private String id;
    private String name;
    private String phoneNumber;
    private String city;
}
