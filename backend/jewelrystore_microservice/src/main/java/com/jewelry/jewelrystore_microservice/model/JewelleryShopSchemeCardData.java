package com.jewelry.jewelrystore_microservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JewelleryShopSchemeCardData {
    private String schemeName;
    private String vasDetails;
    private String customizationOptions;
    private String hallmarkCertified;
    private String redemption;
    private String minimumPayment;
    private String schemeDuration;
}
