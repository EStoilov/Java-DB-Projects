package com.softuni.productshop.web.controller;

import com.google.gson.Gson;
import com.softuni.productshop.domain.dtos.CategorySeedDto;
import com.softuni.productshop.domain.dtos.ProductInRangeDto;
import com.softuni.productshop.domain.dtos.ProductsSeedDto;
import com.softuni.productshop.domain.dtos.UserSeedDto;
import com.softuni.productshop.service.CategoryServiceImpl;
import com.softuni.productshop.service.ProductServiceImpl;
import com.softuni.productshop.service.UserServiceImpl;
import com.softuni.productshop.util.Constants;
import com.softuni.productshop.util.FileReaderUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Controller
public class ProductShopController implements CommandLineRunner {
    
    private final FileReaderUtil fileReaderUtil;
    private final Gson gson;
    private final UserServiceImpl userService;
    private final CategoryServiceImpl categoryService;
    private final ProductServiceImpl productService;

    public ProductShopController(FileReaderUtil fileReaderUtil, Gson gson, UserServiceImpl userService, CategoryServiceImpl categoryService, ProductServiceImpl productService) {
        this.fileReaderUtil = fileReaderUtil;
        this.gson = gson;
        this.userService = userService;
        this.categoryService = categoryService;

        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
//        this.seedUsers();
//        this.seedCategories();
//        this.seedProducts();
        //this.getProductsInRange();
        
    }

    private void getProductsInRange() throws IOException {
        List<ProductInRangeDto> productInRangeDtos = this.productService.getProductsInRange();

        String productsInRangeJson = this.gson.toJson(productInRangeDtos);

        File file = new File("D:\\JavaSoftUniProjects\\JavaDBFundamentals\\ProductShop\\src\\main\\resources\\files\\output\\products-in-range.json");

        FileWriter writer = new FileWriter(file);
        writer.write(productsInRangeJson);
        writer.close();
    }

    private void seedProducts() throws IOException {
        String inputFileContent = this.fileReaderUtil.readContentFile(Constants.PRODUCTS_FILE_PATH);
        ProductsSeedDto[] productsSeedDtos = this.gson.fromJson(inputFileContent, ProductsSeedDto[].class);
        this.productService.seedProducts(productsSeedDtos);
    }

    private void seedCategories() throws IOException {
        String inputFileContent = this.fileReaderUtil.readContentFile(Constants.CATEGORIES_FILE_PATH);
        CategorySeedDto[] categorySeedDtos = this.gson.fromJson(inputFileContent, CategorySeedDto[].class);
        this.categoryService.seedCategories(categorySeedDtos);
    }

    private void seedUsers() throws IOException {
        String inputFileContent = this.fileReaderUtil.readContentFile(Constants.USER_FILE_PATH);

        UserSeedDto[] userSeedDtos = this.gson.fromJson(inputFileContent, UserSeedDto[] .class);
        
         this.userService.seedUsers(userSeedDtos);
    }
}
