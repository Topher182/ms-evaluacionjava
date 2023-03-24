package com.msevaluacionjava.security.service;

import com.msevaluacionjava.security.entity.LoginRol;
import com.msevaluacionjava.security.entity.LoginRolNameEnum;

import java.util.Optional;

public interface LoginRoleService {
     Optional<LoginRol> getByRolNombre(LoginRolNameEnum rolNombre);
     void createRol(LoginRol rol);
}