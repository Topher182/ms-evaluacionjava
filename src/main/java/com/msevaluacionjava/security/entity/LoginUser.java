package com.msevaluacionjava.security.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "LOGIN_USER")
@Data
@NoArgsConstructor
public class LoginUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    private String name;
    @Column(unique = true)
    private String userName;
    @NotNull
    private String password;
    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "LOGIN_USER_LOGIN_ROL", joinColumns = @JoinColumn(name = "login_user_id"),
            inverseJoinColumns = @JoinColumn(name = "login_role_id"))
    private Set<LoginRol> roles = new HashSet<>();

    public LoginUser(@NotNull String name, @NotNull String nombreUsuario, @NotNull String password, @NotNull Set<LoginRol> roles) {
        this.name = name;
        this.userName = nombreUsuario;
        this.password = password;
        this.roles = roles;
    }
}