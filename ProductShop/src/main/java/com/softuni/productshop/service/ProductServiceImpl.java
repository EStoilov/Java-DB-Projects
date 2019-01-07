package com.softuni.productshop.service;

import com.softuni.productshop.domain.dtos.ProductInRangeDto;
import com.softuni.productshop.domain.dtos.ProductsSeedDto;
import com.softuni.productshop.domain.entitie.Category;
import com.softuni.productshop.domain.entitie.Product;
import com.softuni.productshop.domain.entitie.User;
import com.softuni.productshop.repository.CategoryRepository;
import com.softuni.productshop.repository.ProductRepository;
import com.softuni.productshop.repository.UserRepository;
import com.softuni.productshop.util.Constants;
import com.softuni.productshop.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ProductServiceImpl implements ProductService{
    
    private final ProductRepository productRepository; 
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedProducts(ProductsSeedDto[] productsSeedDtos) {
        for (ProductsSeedDto productsSeedDto : productsSeedDtos) {
            if(!(this.validatorUtil.isValid(productsSeedDto))){
                this.validatorUtil.violations(productsSeedDto)
                        .forEach(p -> System.out.println(p.getMessage()));
            } else {
                Product entity = this.modelMapper.map(productsSeedDto, Product.class);
                
                entity.setBuyerId(this.getRandomUser());
                entity.setSellerId(this.getRandomUser());
                entity.setCategories(this.getRandomCategories());
                
                this.productRepository.saveAndFlush(entity);
            }
        } 
    }
    
    private User getRandomUser(){
        Random random = new Random();
        
        return this.userRepository.getOne(random.nextInt((int) this.userRepository.count()));
    }

    private List<Category> getRandomCategories() {
        Random random = new Random();

        List<Category> categories = new ArrayList<>();

        int categoriesCount = random.nextInt((int)this.categoryRepository.count() - 1) + 1;

        for (int i = 0; i < categoriesCount; i++) {
            categories.add(this.categoryRepository.getOne(random.nextInt((int)this.categoryRepository.count() - 1) + 1));
        }

        return categories;
    }

    @Override
    public List<ProductInRangeDto>  getProductsInRange() {
        List<Product> products = this.productRepository
                .findAllByPriceBetweenAndBuyerIdOrderByPrice(
                        Constants.MIN_RANGE_PRICE_PRODUCTS, 
                        Constants.MAX_RANGE_PRICE_PRODUCTS, 
                        null);

        List<ProductInRangeDto> productInRangeDtos = new ArrayList<>();
        for (Product productEntity : products) {
            ProductInRangeDto productInRangeDto = this.modelMapper.map(productEntity, ProductInRangeDto.class);
            productInRangeDto
                    .setSeller(String.format("%s %s", productEntity.getSellerId().getFirstName(), productEntity.getSellerId().getLastName()));

            productInRangeDtos.add(productInRangeDto);
        }

        return productInRangeDtos;
    }
}
