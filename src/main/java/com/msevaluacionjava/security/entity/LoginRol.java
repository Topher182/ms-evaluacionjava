package com.msevaluacionjava.security.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "LOGIN_ROL")
public class LoginRol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private LoginRolNameEnum rolNombre;

    public LoginRol() {
    }

    public LoginRol(@NotNull LoginRolNameEnum rolNombre) {
        this.rolNombre = rolNombre;
    }
}