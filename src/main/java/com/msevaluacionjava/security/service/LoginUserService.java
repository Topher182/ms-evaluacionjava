package com.msevaluacionjava.security.service;

import com.msevaluacionjava.security.entity.LoginCreateUserRequest;
import com.msevaluacionjava.security.entity.LoginUserRequest;
import com.msevaluacionjava.security.entity.LoginCreateUserResponse;
import com.msevaluacionjava.security.entity.LoginUserResponse;

public interface LoginUserService {
    LoginCreateUserResponse createUser(LoginCreateUserRequest request);
    LoginUserResponse loginUser(LoginUserRequest request);
}