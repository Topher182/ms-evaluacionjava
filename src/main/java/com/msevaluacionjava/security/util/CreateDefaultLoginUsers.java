package com.msevaluacionjava.security.util;

import com.msevaluacionjava.security.entity.LoginCreateUserRequest;
import com.msevaluacionjava.security.entity.LoginRolNameEnum;
import com.msevaluacionjava.security.service.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;

@Component
public class CreateDefaultLoginUsers implements CommandLineRunner {

    @Autowired
    LoginUserService userLoginService;

    @Override
    public void run(String... args) {
        LoginCreateUserRequest admin = new LoginCreateUserRequest();
        admin.setName("Cristopher Munoz");
        admin.setUsername("cristopherMunoz");
        admin.setPassword("cmunoz123");
        admin.setRoles(new HashSet<>(Arrays.asList(LoginRolNameEnum.ROLE_ADMIN.name(), LoginRolNameEnum.ROLE_MANAGER.name())));
        userLoginService.createUser(admin);
    }
}