package com.softuni.productshop.service;

import com.softuni.productshop.domain.dtos.UserSeedDto;
import com.softuni.productshop.domain.entitie.User;
import com.softuni.productshop.repository.UserRepository;
import com.softuni.productshop.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    
    private final UserRepository repository;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository repository, ValidatorUtil validatorUtil, ModelMapper modelMapper) {
        this.repository = repository;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedUsers(UserSeedDto[] userSeedDto) {
        for (UserSeedDto seedDto : userSeedDto) {
            if(!this.validatorUtil.isValid(seedDto))  {
                this.validatorUtil.violations(seedDto)
                        .forEach(v -> System.out.println(v.getMessage()));
            } else {
                User user = this.modelMapper.map(seedDto, User.class);
                
                this.repository.saveAndFlush(user);
            }
        }
    }
}
