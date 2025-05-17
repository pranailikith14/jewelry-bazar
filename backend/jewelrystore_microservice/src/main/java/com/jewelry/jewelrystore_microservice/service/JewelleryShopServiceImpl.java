package com.jewelry.jewelrystore_microservice.service;

import com.jewelry.jewelrystore_microservice.exception.ResourceNotFoundException;
import com.jewelry.jewelrystore_microservice.model.JewelleryShop;
import com.jewelry.jewelrystore_microservice.model.JewelleryShopCardData;
import com.jewelry.jewelrystore_microservice.repository.JewelleryShopCardDataRepository;
import com.jewelry.jewelrystore_microservice.repository.JewelleryShopRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JewelleryShopServiceImpl implements JewelleryShopService{

    private final JewelleryShopRepository jewelleryShopRepository;
    private final JewelleryShopCardDataRepository jewelleryShopCardDataRepository;

    public JewelleryShopServiceImpl(JewelleryShopRepository jewelleryShopRepository,
                                    JewelleryShopCardDataRepository jewelleryShopCardDataRepository) {
        this.jewelleryShopRepository = jewelleryShopRepository;
        this.jewelleryShopCardDataRepository = jewelleryShopCardDataRepository;
    }

    @Override
    public List<JewelleryShopCardData> getAllShops() {
        List<JewelleryShopCardData> jewelleryShopsCardData = jewelleryShopCardDataRepository.findAll();
        if (jewelleryShopsCardData.isEmpty()) {
            throw new ResourceNotFoundException("No jewellery shops found.");
        }
        return jewelleryShopsCardData;
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

    @Override
    public void insertJewelleryShopData(JewelleryShop jewelleryShop) {
        if (jewelleryShop == null || jewelleryShop.getBrand() == null || jewelleryShop.getBrand().isBlank()) {
            throw new IllegalArgumentException("Jewellery shop brand cannot be null or empty.");
        }

        //Check if the brand already exists to avoid duplication
        Optional<JewelleryShop> existingShop = jewelleryShopRepository.findByBrand(jewelleryShop.getBrand());
        if (existingShop.isPresent()) {
            throw new IllegalArgumentException("Jewellery shop with this brand already exists.");
        }

        jewelleryShopRepository.save(jewelleryShop);
    }

}
