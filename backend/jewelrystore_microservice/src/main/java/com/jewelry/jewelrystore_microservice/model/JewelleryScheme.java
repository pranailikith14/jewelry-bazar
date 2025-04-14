package com.jewelry.jewelrystore_microservice.model;

import lombok.Data;

import java.util.List;

@Data
public class JewelleryScheme {
    private String name;
    private String type;
    private String duration;
    private String redemptionConditions;
    private String minimumPayment;
    private String bonusOrDiscount;
    private MakingChargesWaiver makingChargesWaiver;
    private boolean gstBenefit;
    private List<String> purityOffered;
    private boolean goldRateProtection;
    private String goldRedemptionPrice;
    private boolean buybackAvailable;
    private String paymentMethods;
    private String gracePeriod;
    private String earlyWithdrawal;
    private boolean loyaltyProgram;
    private boolean customizationAvailable;
    private boolean hallmarkCertified;
    private String postMaturityWindow;
    private String redemptionPlace;
    private String moneyRefund;
}
