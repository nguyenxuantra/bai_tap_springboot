package com.orenda.country.controller;


import com.orenda.country.common.globalDto.CommonResultDto;
import com.orenda.country.dto.auth.LoginRequest;
import com.orenda.country.dto.auth.LoginResponse;
import com.orenda.country.dto.request.UserRequest;
import com.orenda.country.dto.response.UserResponse;
import com.orenda.country.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthResource {
    private final AuthService authService;

    @GetMapping("/login")
    public CommonResultDto<LoginResponse> login(@RequestBody LoginRequest request){
        return CommonResultDto.success(LoginResponse.builder()
                        .token(authService.login(request))
                .build());
    }
    @PostMapping("/account")
    public CommonResultDto<UserResponse> createAccount(@RequestBody UserRequest request){
        return CommonResultDto.success(authService.createUser(request));
    }
}
