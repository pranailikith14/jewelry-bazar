package com.jewelry.jewelrystore_microservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jewelry.jewelrystore_microservice.exception.ResourceNotFoundException;
import com.jewelry.jewelrystore_microservice.model.*;
import com.jewelry.jewelrystore_microservice.service.JewelleryShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;
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
            summary = "Filter jewellery shops by brand and schemes",
            description = "Returns a list of jewellery shops along with matching schemes for up to 3 selected brands"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Filtered shop data retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = BrandSchemeFilterResponse.class)))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input or more than 3 brands provided",
                    content = @Content(schema = @Schema(example = "{\"status\":400, \"message\":\"3 brand names or less than that must be provided.\"}"))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No shops found for the selected brands",
                    content = @Content(schema = @Schema(example = "{\"status\":404, \"message\":\"No jewellery shops found for selected brands.\"}"))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(schema = @Schema(example = "{\"status\":500, \"message\":\"Internal Server Error\"}"))
            )
    })
    @PostMapping("/filter-by-brands-and-schemes")
    public ResponseEntity<com.jewelry.jewelrystore_microservice.model.ApiResponse<List<BrandSchemeFilterResponse>>> getShopsByBrandsAndSchemes(
            @RequestBody List<BrandSchemeFilterRequest> brandSchemeFilterRequestList) {

        log.info("Received filter request for {} brands", brandSchemeFilterRequestList.size());

        try {
            List<BrandSchemeFilterResponse> responseList = jewelleryShopService.getShopsByBrandsAndScheme(brandSchemeFilterRequestList);
            log.info("Successfully filtered {} brand(s) with matching schemes", responseList.size());
            return ResponseEntity.ok(
                    new com.jewelry.jewelrystore_microservice.model.ApiResponse<>(HttpStatus.OK.value(), "Schemes Fetched Successfully", LocalDateTime.now().toString(), responseList)
            );
        } catch (IllegalArgumentException ex) {
            log.warn("Validation error in request: {}", ex.getMessage());
            return ResponseEntity.badRequest().body(
                    new com.jewelry.jewelrystore_microservice.model.ApiResponse<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),LocalDateTime.now().toString(), null));
        } catch (ResourceNotFoundException ex) {
            log.warn("No matching shops found: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new com.jewelry.jewelrystore_microservice.model.ApiResponse<>(HttpStatus.NOT_FOUND.value(), ex.getMessage(), LocalDateTime.now().toString(), null));
        } catch (Exception ex) {
            log.error("Unexpected error during shop filter operation", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new com.jewelry.jewelrystore_microservice.model.ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", LocalDateTime.now().toString(),null));
        }
    }


    @Operation(
            summary = "Insert Jewellery Shop Data",
            description = "Inserts a new jewellery shop with its details and logo as a multipart file"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Data inserted successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{\"status\": 201, \"message\": \"Data inserted successfully\"}"))),
            @ApiResponse(responseCode = "400", description = "Validation Error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{\"status\": 400, \"message\": \"Validation Error\"}"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{\"status\": 500, \"message\": \"Internal Server Error\"}")))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Jewellery shop data in JSON format and logo as a multipart file",
            content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
    )
    @RequestMapping(value = "/insert", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> insertJewelleryShopData(@RequestPart ("jewelleryShopData") String jewelleryShopData,
                                                                       @RequestPart ("multipartFile") MultipartFile multipartFile) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JewelleryShop jewelleryShop = objectMapper.readValue(jewelleryShopData, JewelleryShop.class);
            log.info("Received request to insert jewellery shop: {}", jewelleryShop.getBrand());
            jewelleryShopService.insertJewelleryShopData(jewelleryShop, multipartFile);
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

    @Operation(
            summary = "Insert Jewellery shop card Data",
            description = "Inserts a new jewellery shop along with its logo as a multipart file"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Data inserted successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{\"status\": 201, \"message\": \"Data inserted successfully\"}"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{\"status\": 500, \"message\": \"Internal Server Error\"}")))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Jewellery shop data in JSON format and logo as a multipart file",
            content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
    )
    @RequestMapping(value = "/insertShopCardData", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> insertJewelleryShopCardData(@RequestPart ("jewelleryShopCardData") String jewelleryShopCardData,
                                                                           @RequestPart ("multipartFile") MultipartFile multipartFile) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JewelleryShopCardData jewelleryShopCardData1 = objectMapper.readValue(jewelleryShopCardData, JewelleryShopCardData.class);
            jewelleryShopService.insertJewelleryShopCardData(jewelleryShopCardData1, multipartFile);
            log.info("Successfully inserted shop : {}", jewelleryShopCardData1.getBrand());

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    Map.of("status", HttpStatus.CREATED.value(), "message", "Data inserted successfully")
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
