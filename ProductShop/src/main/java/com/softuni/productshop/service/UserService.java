package com.softuni.productshop.service;

import com.softuni.productshop.domain.dtos.UserSeedDto;
public interface UserService {
    
    void seedUsers(UserSeedDto[] userSeedDto);
}
