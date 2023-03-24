package com.msevaluacionjava.service;

import com.msevaluacionjava.entity.User;
import com.msevaluacionjava.entity.CreateUserRequest;
import com.msevaluacionjava.entity.CreateUserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {
    CreateUserResponse createUser(CreateUserRequest request);
    List<User> getUsers();
    Optional<User> getUserById(String id);
}