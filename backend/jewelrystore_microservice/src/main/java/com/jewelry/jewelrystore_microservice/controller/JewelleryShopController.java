package com.jewelry.jewelrystore_microservice.controller;

import com.jewelry.jewelrystore_microservice.model.JewelleryShop;
import com.jewelry.jewelrystore_microservice.service.JewelleryShopService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/filter-by-brands")
    public ResponseEntity<List<JewelleryShop>> getShopsByBrands(@RequestBody List<String> brands) {
        List<JewelleryShop> jewelleryShops = jewelleryShopService.getShopsByBrands(brands);
        return ResponseEntity.ok(jewelleryShops);
    }

}
