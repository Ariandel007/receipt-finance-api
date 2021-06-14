package com.receiptfinanceapi.dtos;

import lombok.Data;

@Data
public class UserLoginResponse {
    private UserResponse user;
    private String token;
}
