package com.jewelry.jewelrystore_microservice.controller;

import com.jewelry.jewelrystore_microservice.model.JewelleryShop;
import com.jewelry.jewelrystore_microservice.service.JewelleryShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Jewellery Bazar API", description = "Operations related to jewellery shops")
@RestController
@RequestMapping("/api/shops")
public class JewelleryShopController {

    private final JewelleryShopService jewelleryShopService;

    public JewelleryShopController(JewelleryShopService jewelleryShopService) {
        this.jewelleryShopService = jewelleryShopService;
    }

    @Operation(
            summary = "Get all jewellery shops",
            description = "Returns all jewellery shops",
            responses = {
                    @ApiResponse(responseCode = "200", description = "list of jewellery shops"),
                    @ApiResponse(responseCode = "404", description = "No shops found")
            }
    )
    @GetMapping
    public ResponseEntity<List<JewelleryShop>> getAllJewelleryShops() {
        List<JewelleryShop> jewelleryShops = jewelleryShopService.getAllShops();
        return ResponseEntity.ok(jewelleryShops);
    }

    @Operation(
            summary = "Get the jewellery shop data for the selected ones",
            description = "Filter and return jewellery shops for exactly 3 unique brand names",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(type = "string", example = "Tanishq")
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Matching jewellery shops"),
                    @ApiResponse(responseCode = "400", description = "Invalid request (less/more/duplicate brands)"),
                    @ApiResponse(responseCode = "404", description = "No shops found for given brands")
            }
    )
    @PostMapping("/filter-by-brands")
    public ResponseEntity<List<JewelleryShop>> getShopsByBrands(@RequestBody List<String> brands) {
        List<JewelleryShop> jewelleryShops = jewelleryShopService.getShopsByBrands(brands);
        return ResponseEntity.ok(jewelleryShops);
    }

}
