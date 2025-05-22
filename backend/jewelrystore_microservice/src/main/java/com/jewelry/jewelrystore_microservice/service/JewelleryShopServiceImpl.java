package com.jewelry.jewelrystore_microservice.service;

import com.jewelry.jewelrystore_microservice.exception.ResourceNotFoundException;
import com.jewelry.jewelrystore_microservice.model.*;
import com.jewelry.jewelrystore_microservice.repository.JewelleryShopCardDataRepository;
import com.jewelry.jewelrystore_microservice.repository.JewelleryShopRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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
    public List<BrandSchemeFilterResponse> getShopsByBrandsAndScheme(List<BrandSchemeFilterRequest> brandSchemeFilterRequestList) {
        /**
         * to check whether exactly three brands are given or not
         */
        if (brandSchemeFilterRequestList.size() > 3) {
            throw new IllegalArgumentException("3 brand names or less than that must be provided.");
        }

        List<String> brands = brandSchemeFilterRequestList.stream()
                .map(BrandSchemeFilterRequest::getBrand)
                .distinct()
                .collect(Collectors.toUnmodifiableList());

        /**
         * check the uniqueness in the list of brands
         */
        Set<String> normalizedBrands = brands.stream().map(String::toLowerCase).collect(Collectors.toSet());
        if (normalizedBrands.size() != brands.size()) {
            throw new IllegalArgumentException("Brand names must be unique (case-insensitive).");
        }

        List<JewelleryShop> jewelleryShops = jewelleryShopRepository.findByBrandIn(brands);

        if (jewelleryShops.isEmpty()) {
            throw new ResourceNotFoundException("No jewellery shops found for selected brands.");
        }

        List<JewelleryScheme> jewellerySchemeList = new ArrayList<>();

        List<BrandSchemeFilterResponse> brandSchemeFilterResponseList = new ArrayList<>();

        for (BrandSchemeFilterRequest request : brandSchemeFilterRequestList) {
            jewelleryShops.stream()
                    .filter(shop -> shop.getBrand().equalsIgnoreCase(request.getBrand()))
                    .findFirst()
                    .ifPresent(shop -> {
                        List<JewelleryScheme> matchedSchemes = shop.getSchemes().stream()
                                .filter(scheme -> request.getSchemeName().stream()
                                        .anyMatch(s -> s.equalsIgnoreCase(scheme.getName())))
                                .collect(Collectors.toList());

                        BrandSchemeFilterResponse response = new BrandSchemeFilterResponse();
                        response.setJewellerySchemeList(matchedSchemes);
                        response.setLogo(shop.getLogo());
                        response.setStores(shop.getStores());
                        response.setOnlineStoreAvailability(shop.isOnlineStoreAvailability());

                        brandSchemeFilterResponseList.add(response);
                    });
        }

        return brandSchemeFilterResponseList;
    }

    @Override
    public void insertJewelleryShopData(JewelleryShop jewelleryShop, MultipartFile multipartFile) throws IOException {
        if (jewelleryShop == null || jewelleryShop.getBrand() == null || jewelleryShop.getBrand().isBlank()) {
            throw new IllegalArgumentException("Jewellery shop brand cannot be null or empty.");
        }

        //Check if the brand already exists to avoid duplication
        Optional<JewelleryShop> existingShop = jewelleryShopRepository.findByBrand(jewelleryShop.getBrand());
        if (existingShop.isPresent()) {
            throw new IllegalArgumentException("Jewellery shop with this brand already exists.");
        }

        if(multipartFile != null && !multipartFile.isEmpty()) {
            jewelleryShop.setLogo(multipartFile.getBytes());
        }

        jewelleryShopRepository.save(jewelleryShop);
    }

    @Override
    public void insertJewelleryShopCardData(JewelleryShopCardData jewelleryShopCardData, MultipartFile multipartFile) throws IOException {
       if (jewelleryShopCardData == null || jewelleryShopCardData.getBrand() == null || jewelleryShopCardData.getBrand().isBlank()) {
           throw new IllegalArgumentException("Jewellery shop brand cannot be null or empty");
       }

       Optional<JewelleryShopCardData> existingShop = jewelleryShopCardDataRepository.findByBrand(jewelleryShopCardData.getBrand());
       if (existingShop.isPresent()) {
           throw new IllegalArgumentException("Jewellery shop with this brand already exists.");
       }

       if (!multipartFile.isEmpty() && multipartFile != null) {
           jewelleryShopCardData.setLogo(multipartFile.getBytes());
       }

       jewelleryShopCardDataRepository.save(jewelleryShopCardData);
    }

}
