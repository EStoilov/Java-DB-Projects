package com.softuni.gamestore.survice;

import com.softuni.gamestore.domain.dtos.LoginUserDto;
import com.softuni.gamestore.domain.dtos.UserRegisterDto;
public interface UserService {
    
    String registerUser(UserRegisterDto userRegisterDto);
    
    String loginUser(LoginUserDto loginUserDto);
    
    String getUsername(String email);
    
    boolean isAdmin (String email);
}
