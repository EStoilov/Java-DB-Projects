package com.softuni.productshop.service;

import com.softuni.productshop.domain.dtos.CategorySeedDto;
public interface CategoryService {
    
    void seedCategories(CategorySeedDto[] categorySeedDtos);
}
