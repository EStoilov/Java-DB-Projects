package com.softuni.productshop.util;

import java.math.BigDecimal;
public class Constants {
    
    private Constants(){}
    
    public static final String USER_FILE_PATH = "D:\\JavaSoftUniProjects\\JavaDBFundamentals\\ProductShop\\src\\main\\resources\\files\\users.json";
    public static final String PRODUCTS_FILE_PATH = "D:\\JavaSoftUniProjects\\JavaDBFundamentals\\ProductShop\\src\\main\\resources\\files\\products.json";
    public static final String CATEGORIES_FILE_PATH = "D:\\JavaSoftUniProjects\\JavaDBFundamentals\\ProductShop\\src\\main\\resources\\files\\categories.json";
    
    public static final BigDecimal MIN_RANGE_PRICE_PRODUCTS = BigDecimal.valueOf(500);
    public static final BigDecimal MAX_RANGE_PRICE_PRODUCTS = BigDecimal.valueOf(1000);
}
