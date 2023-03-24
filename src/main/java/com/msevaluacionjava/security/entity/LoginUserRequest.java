package com.msevaluacionjava.security.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@ApiModel(value = "LoginUserRequest", description = "Class with data required to login user")
public class LoginUserRequest {

    @ApiModelProperty(value = "username user", position = 1, example = "cristopherMunoz")
    @NotEmpty(message = "required value")
    private String userName;

    @ApiModelProperty(value = "password user", position = 2, example = "cmunoz123")
    @NotEmpty(message = "required value")
    private String password;
}