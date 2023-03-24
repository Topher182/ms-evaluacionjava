package com.msevaluacionjava.security.service;

import com.msevaluacionjava.security.entity.LoginRol;
import com.msevaluacionjava.security.entity.LoginRolNameEnum;
import com.msevaluacionjava.security.entity.LoginUser;
import com.msevaluacionjava.security.entity.LoginUserPrincipal;
import com.msevaluacionjava.security.entity.LoginCreateUserRequest;
import com.msevaluacionjava.security.entity.LoginUserRequest;
import com.msevaluacionjava.security.entity.LoginCreateUserResponse;
import com.msevaluacionjava.security.entity.LoginUserResponse;
import com.msevaluacionjava.security.jwt.JwtProvider;
import com.msevaluacionjava.security.repository.LoginUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LoginUserServiceImpl implements LoginUserService, UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    LoginRoleService loginRoleService;

    @Autowired
    LoginUserRepository loginUserRepository;

    @Value("${jwt.expiration.minutes}")
    private int expirationTokenMinutes;

    @Override
    public LoginCreateUserResponse createUser(LoginCreateUserRequest request) {
        log.info("[createUser] *Starting Operation* request object: {}", request.toString());
        Set<LoginRol> roles = new HashSet<>();
        if (request.getRoles().contains(LoginRolNameEnum.ROLE_ADMIN.name())) {
            roles.add(loginRoleService.getByRolNombre(LoginRolNameEnum.ROLE_ADMIN).get());
        }
        if (request.getRoles().contains(LoginRolNameEnum.ROLE_MANAGER.name())) {
            roles.add(loginRoleService.getByRolNombre(LoginRolNameEnum.ROLE_MANAGER).get());
        }
        if (request.getRoles().contains(LoginRolNameEnum.ROLE_LEADER.name())) {
            roles.add(loginRoleService.getByRolNombre(LoginRolNameEnum.ROLE_LEADER).get());
        }
        LoginUser userLogin = new LoginUser(request.getName(), request.getUsername(),
                passwordEncoder.encode(request.getPassword()), roles);
        loginUserRepository.save(userLogin);
        LoginCreateUserResponse response = LoginCreateUserResponse.builder()
                .name(request.getName())
                .userName(request.getUsername())
                .roles(roles.stream()
                        .map(x -> x.getRolNombre().toString())
                        .collect(Collectors.toSet()))
                .build();
        log.info("[createUser] *Successful Operation* response object: {}", response.toString());
        return response;
    }

    @Override
    public LoginUserResponse loginUser(LoginUserRequest request) {
        log.info("[loginUser] *Starting Operation* request object: {}", request.toString());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jsonWebToken = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        LoginUserResponse response = LoginUserResponse.builder()
                .token(jsonWebToken)
                .type("Bearer")
                .expirationMinutes(expirationTokenMinutes)
                .userName(userDetails.getUsername())
                .authorities(userDetails.getAuthorities())
                .build();
        log.info("[loginUser] *Successful Operation* response object: {}", response.toString());
        return response;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        LoginUser loginUser = loginUserRepository.findByUserName(userName).get();
        return LoginUserPrincipal.build(loginUser);
    }
}