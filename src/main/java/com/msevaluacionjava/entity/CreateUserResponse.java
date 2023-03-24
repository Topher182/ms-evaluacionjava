package com.msevaluacionjava.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.msevaluacionjava.util.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@ApiModel(value = "CreateUserResponse", description = "Class with data response to create user")
public class CreateUserResponse {
    @ApiModelProperty(value = "user id", position = 1, example = "56e5e4ab-4628-420f-ae5e-8dace2c9d215")
    private UUID id;

    @ApiModelProperty(value = "user creation date", position = 2, example = "2023-03-24")
    @JsonFormat(pattern = Constants.DATE_FORMAT_YYYY_MM_DD)
    private Date created;

    @ApiModelProperty(value = "user modification date", position = 3, example = "2023-03-24")
    @JsonFormat(pattern = Constants.DATE_FORMAT_YYYY_MM_DD)
    private Date modified;

    @ApiModelProperty(value = "user creation date", position = 4, example = "2023-03-24")
    @JsonFormat(pattern = Constants.DATE_FORMAT_YYYY_MM_DD)
    private Date last_login;

    @ApiModelProperty(value = "user token", position = 5, example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjcmlzdG9waGVyTXVub3oiLCJpYXQiOjE2Nzk2MzI5NDYsImV4cCI6MTY3OTYzMzg0Nn0.KGSAg72c711B2rP912gN8TO_PEDuhXLjEELCBLNv82QI_O6vQkgY31xix6T1N9JAvGtAMmL9LVdWVI6SQID4rA")
    private String token;

    @ApiModelProperty(value = "user active", position = 6, example = "true")
    private boolean isActive;
}