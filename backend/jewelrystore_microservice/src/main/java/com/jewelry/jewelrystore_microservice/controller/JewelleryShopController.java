package com.jewelry.jewelrystore_microservice.controller;

import com.jewelry.jewelrystore_microservice.model.JewelleryShop;
import com.jewelry.jewelrystore_microservice.service.JewelleryShopService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/shops")
public class JewelleryShopController {

    private final JewelleryShopService jewelleryShopService;

    public JewelleryShopController(JewelleryShopService jewelleryShopService) {
        this.jewelleryShopService = jewelleryShopService;
    }

    @GetMapping
    public ResponseEntity<List<JewelleryShop>> getAllJewelleryShops() {
        List<JewelleryShop> jewelleryShops = jewelleryShopService.getAllShops();
        return ResponseEntity.ok(jewelleryShops);
    }
}
