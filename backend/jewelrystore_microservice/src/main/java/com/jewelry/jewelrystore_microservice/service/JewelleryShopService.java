package com.jewelry.jewelrystore_microservice.service;

import com.jewelry.jewelrystore_microservice.model.JewelleryShop;
import com.jewelry.jewelrystore_microservice.model.JewelleryShopCardData;

import java.util.List;

public interface JewelleryShopService {

    List<JewelleryShopCardData> getAllShops();

    List<JewelleryShop> getShopsByBrands(List<String> brands);

    void insertJewelleryShopData(JewelleryShop jewelleryShop);
}
