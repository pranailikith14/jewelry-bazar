package com.jewelry.jewelrystore_microservice.service;

import com.jewelry.jewelrystore_microservice.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface JewelleryShopService {

    List<JewelleryShopCardData> getAllShops();

    List<BrandSchemeFilterResponse> getShopsByBrandsAndScheme(List<BrandSchemeFilterRequest> brandSchemeFilterRequestList);

    void insertJewelleryShopData(JewelleryShop jewelleryShop, MultipartFile multipartFile) throws IOException;

    void insertJewelleryShopCardData(JewelleryShopCardData jewelleryShopCardData, MultipartFile multipartFile) throws IOException;
}
