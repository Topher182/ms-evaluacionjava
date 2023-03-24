package com.msevaluacionjava.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@ApiModel(value = "CreateUserRequest", description = "Class with data required to create user")
public class CreateUserRequest {
    @NotEmpty(message = "required value")
    @ApiModelProperty(value = "user name", position = 1, example = "Juan Rodriguez")
    private String name;

    @NotEmpty(message = "required value")
    @ApiModelProperty(value = "user email", position = 2, example = "juan@rodriguez.org")
    private String email;

    @NotEmpty(message = "required value")
    @ApiModelProperty(value = "user password", position = 3, example = "JuanRod123!")
    private String password;

    @Valid
    @NotEmpty(message = "required value")
    @ApiModelProperty(value = "phone list", position = 4)
    private List<PhoneDto> phones;

    @Data
    @Builder
    @ApiModel(value = "PhoneDto", description = "Class with data required to create user")
    public static class PhoneDto {

        @NotEmpty(message = "required value")
        @ApiModelProperty(value = "user number", position = 1, example = "123456789")
        private String number;

        @NotEmpty(message = "required value")
        @ApiModelProperty(value = "user city code", position = 2, example = "1")
        private String cityCode;

        @NotEmpty(message = "required value")
        @ApiModelProperty(value = "user country code", position = 3, example = "100")
        private String countryCode;
    }
}