package com.msevaluacionjava.security.repository;

import com.msevaluacionjava.security.entity.LoginRolNameEnum;
import com.msevaluacionjava.security.entity.LoginRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRoleRepository extends JpaRepository<LoginRol, Integer> {
    Optional<LoginRol> findByRolNombre(LoginRolNameEnum rolNombre);
}