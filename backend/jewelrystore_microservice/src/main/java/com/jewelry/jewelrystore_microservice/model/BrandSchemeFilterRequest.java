package com.jewelry.jewelrystore_microservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandSchemeFilterRequest {
    private String brand;
    private List<String> schemeName;
}
