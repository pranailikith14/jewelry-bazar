package com.jewelry.jewelrystore_microservice.service;

import com.jewelry.jewelrystore_microservice.exception.ResourceNotFoundException;
import com.jewelry.jewelrystore_microservice.model.JewelleryShop;
import com.jewelry.jewelrystore_microservice.repository.JewelleryShopRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JewelleryShopServiceImpl implements JewelleryShopService{

    private final JewelleryShopRepository jewelleryShopRepository;

    public JewelleryShopServiceImpl(JewelleryShopRepository jewelleryShopRepository) {
        this.jewelleryShopRepository = jewelleryShopRepository;
    }

    @Override
    public List<JewelleryShop> getAllShops() {
        List<JewelleryShop> jewelleryShops = jewelleryShopRepository.findAll();
        if (jewelleryShops.isEmpty()) {
            throw new ResourceNotFoundException("No jewellery shops found.");
        }
        return jewelleryShops;
    }

    @Override
    public List<JewelleryShop> getShopsByBrands(List<String> brands) {
        /**
         * to check whether exactly three brands are given or not
         */
        if (brands == null || brands.size() != 3) {
            throw new IllegalArgumentException("Exactly 3 brand names must be provided.");
        }
        /**
         * check the uniqueness in the list of brands
         */
        Set<String> normalizedBrands = brands.stream().map(String::toLowerCase).collect(Collectors.toSet());
        if (normalizedBrands.size() != 3) {
            throw new IllegalArgumentException("Brand names must be unique (case-insensitive).");
        }
        List<JewelleryShop> jewelleryShops = jewelleryShopRepository.findByBrandIn(brands);
        if (jewelleryShops.isEmpty()) {
            throw new ResourceNotFoundException("No jewellery shops found for selected brands.");
        }
        return jewelleryShops;
    }

}
