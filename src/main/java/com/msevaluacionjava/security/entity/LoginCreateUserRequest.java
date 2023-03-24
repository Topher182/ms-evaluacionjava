package com.msevaluacionjava.security.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
public class LoginCreateUserRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private Set<String> roles = new HashSet<>();
}