package com.msevaluacionjava.security.repository;

import com.msevaluacionjava.security.entity.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginUserRepository extends JpaRepository<LoginUser, Integer> {
    Optional<LoginUser> findByUserName(String nombreUsuario);
    boolean existsByUserName(String nombreUsuario);
}