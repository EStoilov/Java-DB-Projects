package com.softuni.productshop.service;

import com.softuni.productshop.domain.dtos.CategorySeedDto;
import com.softuni.productshop.domain.entitie.Category;
import com.softuni.productshop.repository.CategoryRepository;
import com.softuni.productshop.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(ValidatorUtil validatorUtil, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories(CategorySeedDto[] categorySeedDtos) {
        for (CategorySeedDto categorySeedDto : categorySeedDtos) {
            if(!(this.validatorUtil.isValid(categorySeedDto))){
                this.validatorUtil.violations(categorySeedDto)
                        .forEach(c -> System.out.println(c.getMessage()));
            } else {
                Category entity = this.modelMapper.map(categorySeedDto, Category.class);
                this.categoryRepository.saveAndFlush(entity);
            }
        } 
    }
}
