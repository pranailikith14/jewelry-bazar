package com.jewelry.jewelrystore_microservice.repository;

import com.jewelry.jewelrystore_microservice.model.JewelleryShop;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JewelleryShopRepository extends MongoRepository<JewelleryShop, String> {

    List<JewelleryShop> findByBrandIn(List<String> brands);
}
