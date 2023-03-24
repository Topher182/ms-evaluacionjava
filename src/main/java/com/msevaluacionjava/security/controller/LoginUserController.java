package com.msevaluacionjava.security.controller;

import com.msevaluacionjava.security.entity.ErrorResponse;
import com.msevaluacionjava.security.entity.LoginUserRequest;
import com.msevaluacionjava.security.entity.LoginUserResponse;
import com.msevaluacionjava.security.service.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class LoginUserController {

    @Autowired
    LoginUserService userLoginService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<LoginUserResponse> loginUser(@Valid @RequestBody LoginUserRequest request) {
        LoginUserResponse response = userLoginService.loginUser(request);
        return new ResponseEntity(response, HttpStatus.OK);
    }
}