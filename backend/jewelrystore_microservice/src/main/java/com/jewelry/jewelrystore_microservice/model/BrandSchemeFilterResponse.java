package com.jewelry.jewelrystore_microservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandSchemeFilterResponse {
    private List<JewelleryScheme> jewellerySchemeList;
    private byte[] logo;
    private String stores;
    private boolean onlineStoreAvailability;
}
