package com.receiptfinanceapi.controllers;

import com.receiptfinanceapi.dtos.UserLoginRequest;
import com.receiptfinanceapi.dtos.UserLoginResponse;
import com.receiptfinanceapi.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest userLogin) {
        try {
            UserLoginResponse userLoginResponse = this.userService.login(userLogin);
            return ResponseEntity.status(HttpStatus.OK).body(userLoginResponse);
        } catch (Exception e) {
            //e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody UserLoginRequest userLogin) {
        try {
            UserLoginResponse userLoginResponse = this.userService.register(userLogin);
            return ResponseEntity.status(HttpStatus.OK).body(userLoginResponse);
        } catch (Exception e) {
            //e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

}
