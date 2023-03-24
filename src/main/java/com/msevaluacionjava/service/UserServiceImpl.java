package com.msevaluacionjava.service;

import com.msevaluacionjava.entity.CreateUserRequest;
import com.msevaluacionjava.entity.CreateUserResponse;
import com.msevaluacionjava.entity.User;
import com.msevaluacionjava.exception.BusinessException;
import com.msevaluacionjava.exception.RequestException;
import com.msevaluacionjava.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpHeaders headers;

    @Value("${validation.email.regex}")
    private String emailRegex;

    @Value("${validation.email.invalid-error-message}")
    private String invalidEmailErrorMessage;

    @Value("${validation.password.regex}")
    private String passwordRegex;

    @Value("${validation.password.invalid-error-message}")
    private String invalidPasswordErrorMessage;

    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {
        log.info("[createUser] *Starting Operation* request object: {}", request.toString());
        validateCreateUserRequest(request);
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User newUser = objectMapper.convertValue(request, User.class);
        User saveUser = userRepository.save(newUser);
        CreateUserResponse response = CreateUserResponse.builder()
                .id(saveUser.getId())
                .created(saveUser.getCreateAt())
                .modified(saveUser.getModified())
                .last_login(saveUser.getLastLogin())
                .token(headers.get("Authorization").get(0))
                .isActive(saveUser.isActive())
                .build();
        log.info("[createUser] *Successful Operation* response object: {}", response.toString());
        return response;
    }

    @Override
    public List<User> getUsers() {
        log.info("[getUsers] *Starting Operation* without request");
        List<User> response = userRepository.findAll();
        log.info("[getUsers] *Successful Operation* response object: {}", response.toString());
        return response;
    }

    @Override
    public Optional<User> getUserById(String id) {
        log.info("[getUserById] *Starting Operation* request id: {}", id);
        Optional<User> response = userRepository.findById(UUID.fromString(id));
        log.info("[getUserById] *Successful Operation* response object: {}", response.toString());
        return response;
    }

    public void validateCreateUserRequest(CreateUserRequest request) {
        validateEmailFormat(request.getEmail());
        validateEmailExistence(request.getEmail());
        validatePassword(request.getPassword());
    }

    public void validateEmailFormat(String email) {
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email.trim());
        if (!matcher.matches()) {
            throw new RequestException(invalidEmailErrorMessage);
        }
    }

    public void validateEmailExistence(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Email ya existe");
        }
    }

    public void validatePassword(String password) {
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password.trim());
        if (!matcher.matches()) {
            throw new RequestException(invalidPasswordErrorMessage);
        }
    }
}