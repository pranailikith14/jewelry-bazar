package com.jewelry.jewelrystore_microservice.service;

import com.jewelry.jewelrystore_microservice.exception.ResourceNotFoundException;
import com.jewelry.jewelrystore_microservice.model.JewelleryShop;
import com.jewelry.jewelrystore_microservice.repository.JewelleryShopRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        List<JewelleryShop> jewelleryShops = jewelleryShopRepository.findByBrandIn(brands);
        if (jewelleryShops.isEmpty()) {
            throw new ResourceNotFoundException("No jewellery shops found for selected brands.");
        }
        return jewelleryShops;
    }

}
