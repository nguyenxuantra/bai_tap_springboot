package com.orenda.country.service;

import com.orenda.country.dto.auth.LoginRequest;
import com.orenda.country.dto.auth.LoginResponse;
import com.orenda.country.dto.request.UserRequest;
import com.orenda.country.dto.response.UserResponse;

public interface AuthService {
    String login(LoginRequest request);
    UserResponse createUser(UserRequest request);
}
