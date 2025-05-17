package com.jewelry.jewelrystore_microservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class JewelleryScheme {
    private String name;
    private String type;
    private String redemptionConditions;
    private String minimumPayment;
    private String duration;
    private String bonusOrMakingChargesWaiver;
    private boolean gstBenefit;
    private List<String> purityOffered;
    private boolean goldRateProtection;
    private String goldRedemptionPrice;
    private boolean buybackAvailable;
    private String paymentMethods;
    private String onlinePaymentFacility;
    private String earlyWithdrawal;
    private boolean loyaltyBenefits;
    private boolean customizationAvailable;
    private boolean hallmarkCertified;
    private String postMaturityPurchaseWindow;
    private String refundPolicy;
    private String schemeRedemptionPlace;
    private String customerSupport;
}
