package com.msevaluacionjava.security.service;

import com.msevaluacionjava.security.entity.LoginRolNameEnum;
import com.msevaluacionjava.security.repository.LoginRoleRepository;
import com.msevaluacionjava.security.entity.LoginRol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class LoginRoleServiceImpl implements LoginRoleService {

    @Autowired
    LoginRoleRepository loginRoleRepository;

    public Optional<LoginRol> getByRolNombre(LoginRolNameEnum rolNombre){
        return loginRoleRepository.findByRolNombre(rolNombre);
    }

    public void createRol(LoginRol loginRol){
        loginRoleRepository.save(loginRol);
    }
}