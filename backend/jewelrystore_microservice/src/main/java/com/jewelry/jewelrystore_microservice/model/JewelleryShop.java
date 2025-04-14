package com.jewelry.jewelrystore_microservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "jewellery_schemes")
public class JewelleryShop {
    @Id
    private String id;
    private String brand;
    private int yearEstablished;
    private String stores;
    private boolean onlineStore;
    private String contactSupport;
    private List<JewelleryScheme> schemes;
}
