package com.jewelry.jewelrystore_microservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "jewellery_schemes")
public class JewelleryShop {
    @Id
    private String id;
    private String brand;
    private int yearEstablished;
    private String stores;
    private boolean onlineStoreAvailability;
    private byte[] logo;
    private List<JewelleryScheme> schemes;
}
