package com.msevaluacionjava.security.util;

import com.msevaluacionjava.security.service.LoginRoleService;
import com.msevaluacionjava.security.entity.LoginRolNameEnum;
import com.msevaluacionjava.security.entity.LoginRol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CreateDefaultLoginRoles implements CommandLineRunner {

    @Autowired
    LoginRoleService rolLoginService;

    @Override
    public void run(String... args) {
        LoginRol rolAdmin = new LoginRol(LoginRolNameEnum.ROLE_ADMIN);
        LoginRol rolUser = new LoginRol(LoginRolNameEnum.ROLE_MANAGER);
        LoginRol rolLeader = new LoginRol(LoginRolNameEnum.ROLE_LEADER);
        rolLoginService.createRol(rolAdmin);
        rolLoginService.createRol(rolUser);
        rolLoginService.createRol(rolLeader);
    }
}