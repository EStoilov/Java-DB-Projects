package com.softuni.gamestore.survice;

import com.softuni.gamestore.domain.dtos.LoginUserDto;
import com.softuni.gamestore.domain.dtos.UserRegisterDto;
import com.softuni.gamestore.domain.entities.Role;
import com.softuni.gamestore.domain.entities.User;
import com.softuni.gamestore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private ModelMapper modelMapper;


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public String registerUser(UserRegisterDto userRegisterDto) {

        Validator validator = 
                Validation.byDefaultProvider()
                .configure().buildValidatorFactory().getValidator();

        StringBuilder sb = new StringBuilder();
        
        if(!(userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword()))){
            sb.append("Password don't match!");
        } else if (validator.validate(userRegisterDto).size() > 0){
            for (ConstraintViolation<UserRegisterDto> violation : validator.validate(userRegisterDto)) {
                sb.append(violation.getMessage()).append(System.lineSeparator());
            } 
        } else {
            
            User user = this.userRepository.findByEmail(userRegisterDto.getEmail()).orElse(null);
            
            if (user != null){
                sb.append("User already exist !");
                return sb.toString().trim();
            }
            
            user = this.modelMapper.map(userRegisterDto, User.class);
            
             if(this.userRepository.count() == 0){
                 user.setRole(Role.ADMIN);
             } else {
                 user.setRole(Role.USER);
             }
             
             this.userRepository.saveAndFlush(user);
             
             sb.append(String.format("%s was registered", user.getFullName()));
            
        }
        
        return sb.toString().trim();
        
    }

    @Override
    public String loginUser(LoginUserDto loginUserDto) {

        Validator validator =
                Validation.byDefaultProvider()
                        .configure().buildValidatorFactory().getValidator();
        
        Set<ConstraintViolation<LoginUserDto>> validations = validator.validate(loginUserDto);
        
        StringBuilder sb = new StringBuilder();
        
        User user;
        
        if(validations.size() > 0){
            for (ConstraintViolation<LoginUserDto> validation : validations) {
                sb.append(validation.getMessage())
                        .append(System.lineSeparator());
            }
        } else {
            user = this.userRepository.findByEmail(loginUserDto.getEmail()).orElse(null);
            
            if(user == null){
                sb.append("User does not exist!");
            } else {
                if(!(user.getPassword().equals(loginUserDto.getPassword()))){
                    sb.append("Wrong Password!");
                }else {
                 sb.append(String.format("Successfully logged in %s", user.getFullName()));   
                }
            }
        }
        return sb.toString().trim();
    }

    @Override
    public String getUsername(String email) {
        
        User user = this.userRepository.findByEmail(email).orElse(null);
        
        return user.getFullName();
    }

    @Override
    public boolean isAdmin(String email) {
        
        User user = this.userRepository.findByEmail(email).orElse(null);
        
        return user.getRole().equals("ADMIN");
    }
}
