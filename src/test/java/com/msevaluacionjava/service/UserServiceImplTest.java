package com.msevaluacionjava.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msevaluacionjava.entity.CreateUserRequest;
import com.msevaluacionjava.entity.CreateUserResponse;
import com.msevaluacionjava.entity.Phone;
import com.msevaluacionjava.entity.User;
import com.msevaluacionjava.exception.BusinessException;
import com.msevaluacionjava.exception.RequestException;
import com.msevaluacionjava.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class UserServiceImplTest {

    @InjectMocks
    private UserService userService;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    private HttpHeaders headers;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ObjectMapper objectMapper;

    private final String C_EMAIL_REGEX = "^(([^<>()\\[\\]\\.,;:\\s@\\\"]+(\\.[^<>()\\[\\]\\.,;:\\s@\\\"]+)*)|(\\\".+\\\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,3}))$";

    private final String C_INVALID_EMAIL_ERROR_MESSAGE = "El email debe contener un formato válido";

    private final String C_PASSWORD_REGEX = "^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}$";

    private final String C_INVALID_PASSWORD_ERROR_MESSAGE = "La contraseña debe tener al entre 8 y 16 caracteres, al menos un dígito, al menos una minúscula y al menos una mayúscula";

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl();
        ReflectionTestUtils.setField(userService, "emailRegex", C_EMAIL_REGEX);
        ReflectionTestUtils.setField(userService, "invalidEmailErrorMessage", C_INVALID_EMAIL_ERROR_MESSAGE);
        ReflectionTestUtils.setField(userService, "passwordRegex", C_PASSWORD_REGEX);
        ReflectionTestUtils.setField(userService, "invalidPasswordErrorMessage", C_INVALID_PASSWORD_ERROR_MESSAGE);
        initMocks(this);
    }

    @Test
    void createUserSuccess() {
        when(headers.get("Authorization")).thenReturn(Collections.singletonList("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjcmlzdG9waGVyTXVub3oiLCJpYXQiOjE2Nzk2MzQyMzksImV4cCI6MTY3OTYzNTEzOX0.Csrr27z9BJg_XcHy__5sRe8yJJIqjcDJAswpBQHTTK9JZ1oUVySsd6XMGswEdqjP4SLnOP8-PJvEFYobnpxp3w"));
        when(passwordEncoder.encode(anyString())).thenReturn("$2a$10$sL78ECqoetLSJmkaM8P7zecrx7Nt4kD4fTL4p7TRPaLnsV2d.OKue");
        when(objectMapper.convertValue(anyObject(), any(Class.class))).thenReturn(getDefaultUser());
        when(userRepository.findByEmail("")).thenReturn(getDefaultUser());
        when(userRepository.save(anyObject())).thenReturn(getDefaultUser());
        CreateUserResponse createUser = userService.createUser(getCreateUserRequest("juan@rodriguez.org", "JuanRodri123#"));
        Assertions.assertNotNull(createUser);
    }

    @Test
    void createUserWithInvalidEmailFormat() {
        Exception exception = Assertions.assertThrows(RequestException.class, () ->
                userService.createUser(getCreateUserRequest("juan@.com", "JuanRodri123#")));
        String errorMessage = exception.getMessage();
        Assertions.assertNotNull(errorMessage);
        Assertions.assertEquals(errorMessage, C_INVALID_EMAIL_ERROR_MESSAGE);
    }

    @Test
    void createUserWithEmailExistence() {
        when(userRepository.findByEmail(anyString())).thenReturn(getDefaultUser());
        Exception exception = Assertions.assertThrows(BusinessException.class, () ->
                userService.createUser(getCreateUserRequest("juan@rodriguez.org", "JuanRodri123#")));
        String errorMessage = exception.getMessage();
        Assertions.assertNotNull(errorMessage);
        Assertions.assertEquals("Email ya existe", errorMessage);
    }

    @Test
    void createUserWithInvalidPassword() {
        Exception exception = Assertions.assertThrows(RequestException.class, () ->
                userService.createUser(getCreateUserRequest("juan@rodriguez.org", "Juan")));
        String errorMessage = exception.getMessage();
        Assertions.assertNotNull(errorMessage);
        Assertions.assertEquals(errorMessage, C_INVALID_PASSWORD_ERROR_MESSAGE);
    }

    @Test
    void getUsers() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(getDefaultUser()));
        List<User> usersList = userService.getUsers();
        Assertions.assertNotNull(usersList);
    }

    @Test
    void getUserById() {
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(getDefaultUser()));
        Optional<User> user = userService.getUserById("dca8e656-8a32-11ed-a1eb-0242ac120002");
        Assertions.assertNotNull(user);
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

    CreateUserRequest getCreateUserRequest(String email, String password) {
        return CreateUserRequest.builder()
                .name("Juan Rodriguez")
                .email(email)
                .password(password)
                .phones(
                        Collections.singletonList(CreateUserRequest.PhoneDto.builder()
                                .number("1234567")
                                .cityCode("1")
                                .countryCode("57")
                                .build())
                )
                .build();
    }
}