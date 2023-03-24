package com.msevaluacionjava.security.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@Builder
@ToString
@ApiModel(value = "LoginUserResponse", description = "Class with data response to login user")
public class LoginUserResponse {

    @ApiModelProperty(value = "token", position = 1, example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjbXVub3oiLCJpYXQiOjE2Nzk2MjA1ODgsImV4cCI6MTY3OTYyMTQ4OH0.FykD9Z6fWu-nmhvuAlIzgi6cR8TXv14iDrv-Oy8e10GYzd1dpgt2glxUdBTSKFjX9SjlExkw2lt7Rd4n6YTzhQ")
    private String token;

    @ApiModelProperty(value = "token type", position = 2, example = "Bearer")
    private String type;

    @ApiModelProperty(value = "token expiration minutes", position = 3, example = "15")
    private int expirationMinutes;

    @ApiModelProperty(value = "token user name", position = 4, example = "cmunoz")
    private String userName;

    @ApiModelProperty(value = "token authorities", position = 5, example = "ROLE_ADMIN")
    private Collection<? extends GrantedAuthority> authorities;
}