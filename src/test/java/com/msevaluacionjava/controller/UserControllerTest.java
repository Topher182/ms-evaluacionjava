package com.msevaluacionjava.controller;

import com.msevaluacionjava.entity.CreateUserRequest;
import com.msevaluacionjava.entity.CreateUserResponse;
import com.msevaluacionjava.entity.Phone;
import com.msevaluacionjava.entity.User;
import com.msevaluacionjava.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createUserSuccess() {
        ResponseEntity<CreateUserResponse> response = userController.createUser(getCreateUserRequest(), "Bearer token");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void getUsersFoundsSuccess() {
        when(userService.getUsers()).thenReturn(Collections.singletonList(getDefaultUser()));
        ResponseEntity<List<User>> response = userController.getUsers("Bearer token");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void getUsersNotFoundsSuccess() {
        when(userService.getUsers()).thenReturn(Collections.emptyList());
        ResponseEntity<List<User>> response = userController.getUsers("Bearer token");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Assertions.assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getUserByIdFoundSuccess() {
        when(userService.getUserById(anyString())).thenReturn(Optional.of(getDefaultUser()));
        ResponseEntity<User> response = userController.getUserById("dca8e656-8a32-11ed-a1eb-0242ac120002","Bearer token");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void getUserByIdNotFoundSuccess() {
        when(userService.getUserById(anyString())).thenReturn(Optional.empty());
        ResponseEntity<User> response = userController.getUserById("dca8e656-8a32-11ed-a1eb-0242ac120002", "Bearer token");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Assertions.assertEquals(Optional.empty(), response.getBody());
    }

    CreateUserRequest getCreateUserRequest() {
        return CreateUserRequest.builder()
                .name("Juan Rodriguez")
                .email("juan@rodriguez.org")
                .password("JuanRod123!")
                .phones(
                        Collections.singletonList(CreateUserRequest.PhoneDto.builder()
                                .number("1234567")
                                .cityCode("1")
                                .countryCode("57")
                                .build())
                )
                .build();
    }

    User getDefaultUser() {
        return User.builder()
                .id(UUID.fromString("dca8e656-8a32-11ed-a1eb-0242ac120002"))
                .name("Juan Rodriguez")
                .email("juan@rodriguez.org")
                .password("JuanRod123!")
                .createAt(new Date())
                .modified(new Date())
                .lastLogin(new Date())
                .isActive(true)
                .phones(
                        Collections.singletonList(Phone.builder()
                                .id(UUID.fromString("6c19850d-16c6-4609-8c55-d94ae0159178"))
                                .number("1234567")
                                .cityCode("0")
                                .countryCode("0")
                                .build())
                )
                .build();
    }
}