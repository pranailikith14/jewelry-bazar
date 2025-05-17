package com.jewelry.jewelrystore_microservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "jewellery_shop_card_data")
public class JewelleryShopCardData {
    @Id
    private String id;
    private String brand;
    private String stores;
    private List<JewelleryShopSchemeCardData> schemes;
}
