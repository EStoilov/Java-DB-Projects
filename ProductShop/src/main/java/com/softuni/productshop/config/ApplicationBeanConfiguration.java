package com.softuni.productshop.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.softuni.productshop.util.FileReaderUtil;
import com.softuni.productshop.util.FileReaderUtilImpl;
import com.softuni.productshop.util.ValidatorUtil;
import com.softuni.productshop.util.ValidatorUtilImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public FileReaderUtil fileIOUtil() {
        return new FileReaderUtilImpl();
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    }

    @Bean
    public ValidatorUtil validatorUtil() {
        return new ValidatorUtilImpl();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
