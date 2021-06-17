package com.receiptfinanceapi.services;

import com.receiptfinanceapi.dtos.UserLoginRequest;
import com.receiptfinanceapi.dtos.UserLoginResponse;
import com.receiptfinanceapi.dtos.UserResponse;

public interface IUserService {
    UserLoginResponse login(UserLoginRequest userLoginRequest) throws Exception ;
    UserLoginResponse register(UserLoginRequest userRegisterRequest) throws Exception;
    UserResponse findUserById(Long id) throws Exception;

}
