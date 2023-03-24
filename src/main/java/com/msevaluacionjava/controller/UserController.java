package com.msevaluacionjava.controller;

import com.msevaluacionjava.entity.User;
import com.msevaluacionjava.entity.CreateUserRequest;
import com.msevaluacionjava.entity.CreateUserResponse;
import com.msevaluacionjava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request, @RequestHeader(value = "Authorization") String bearerToken) {
        CreateUserResponse response = userService.createUser(request);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsers(@RequestHeader(value = "Authorization") String bearerToken) {
        List<User> response = userService.getUsers();
        return new ResponseEntity(response, !response.isEmpty() ? HttpStatus.OK : HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable("id") String id,  @RequestHeader(value = "Authorization") String bearerToken) {
        Optional<User> response = userService.getUserById(id);
        return new ResponseEntity(response, response.isPresent() ? HttpStatus.OK : HttpStatus.NO_CONTENT);
    }
}