package com.jewelry.jewelrystore_microservice.controller;

import com.jewelry.jewelrystore_microservice.model.JewelleryShop;
import com.jewelry.jewelrystore_microservice.model.JewelleryShopCardData;
import com.jewelry.jewelrystore_microservice.service.JewelleryShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Tag(name = "Jewellery Bazar API", description = "Operations related to jewellery shops")
@RestController
@RequestMapping("/api/shops")
@Slf4j
public class JewelleryShopController {

    private final JewelleryShopService jewelleryShopService;

    public JewelleryShopController(JewelleryShopService jewelleryShopService) {
        this.jewelleryShopService = jewelleryShopService;
    }

    @Operation(
            summary = "Get all jewellery shops card data",
            description = "Returns all jewellery shops that we present on the initial card",
            responses = {
                    @ApiResponse(responseCode = "200", description = "list of jewellery shops"),
                    @ApiResponse(responseCode = "404", description = "No shops found")
            }
    )
    @GetMapping
    public ResponseEntity<List<JewelleryShopCardData>> getAllJewelleryShops() {
        List<JewelleryShopCardData> jewelleryShopCardData = jewelleryShopService.getAllShops();
        return ResponseEntity.ok(jewelleryShopCardData);
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

    @Operation(
            summary = "Insert a Jewellery Shop",
            description = "Add a new jewellery shop with all its schemes",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Data inserted successfully",
                            content = @Content(schema = @Schema(type = "object"))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data"),
                    @ApiResponse(responseCode = "500", description = "Server error")
            }
    )
    @PostMapping("/insert")
    public ResponseEntity<Map<String, Object>> insertJewelleryShopData(@Valid @RequestBody JewelleryShop jewelleryShop) {
        try {
            log.info("Received request to insert jewellery shop: {}", jewelleryShop.getBrand());
            jewelleryShopService.insertJewelleryShopData(jewelleryShop);
            log.info("Successfully inserted shop: {}", jewelleryShop.getBrand());

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    Map.of("status", HttpStatus.CREATED.value(), "message", "Data inserted successfully")
            );
        }
        catch (IllegalArgumentException illegalArgumentException) {
            log.error("Validation Error: {}", illegalArgumentException.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Map.of("status", HttpStatus.BAD_REQUEST.value(), "message", illegalArgumentException.getMessage())
            );
        }
        catch (Exception exception) {
            log.error("Error while inserting shop: {}", exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("status", HttpStatus.INTERNAL_SERVER_ERROR.value(), "message", "Internal Server Error")
            );
        }
    }

}
