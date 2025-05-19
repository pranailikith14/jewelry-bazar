package com.jewelry.jewelrystore_microservice.repository;

import com.jewelry.jewelrystore_microservice.model.JewelleryShopCardData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface JewelleryShopCardDataRepository extends MongoRepository<JewelleryShopCardData, String> {

    Optional<JewelleryShopCardData> findByBrand(String brand);
}
