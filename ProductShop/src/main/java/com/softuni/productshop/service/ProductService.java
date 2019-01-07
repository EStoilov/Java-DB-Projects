package com.softuni.productshop.service;

import com.softuni.productshop.domain.dtos.ProductInRangeDto;
import com.softuni.productshop.domain.dtos.ProductsSeedDto;

import java.util.List;
public interface ProductService {
    
    void seedProducts(ProductsSeedDto[] productsSeedDtos);

    List<ProductInRangeDto> getProductsInRange();
}
