package com.msevaluacionjava.security.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Set;

@Data
@Builder
@ToString
public class LoginCreateUserResponse {
    private String name;
    private String userName;
    private Set<String> roles;
}