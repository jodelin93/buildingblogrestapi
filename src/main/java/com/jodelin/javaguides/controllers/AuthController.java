package com.jodelin.javaguides.controllers;

import com.jodelin.javaguides.dtos.LoginDto;
import com.jodelin.javaguides.dtos.RegisterUserDto;
import com.jodelin.javaguides.services.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthController {
    private final LoginService loginService;
    @PostMapping({"login","signing"})
    public String login(@RequestBody LoginDto loginDto){
        return  loginService.loginUser(loginDto);
    }

    @PostMapping("register")
    public String register(@RequestBody RegisterUserDto registerUserDto){
        return  loginService.registerUser(registerUserDto);
    }
}
