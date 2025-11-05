package com.orenda.country.service.impl;

import com.orenda.country.common.security.jwt.JwtClaimNames;
import com.orenda.country.common.security.jwt.JwtService;
import com.orenda.country.dto.auth.LoginRequest;
import com.orenda.country.dto.auth.LoginResponse;
import com.orenda.country.dto.request.UserRequest;
import com.orenda.country.dto.response.UserResponse;
import com.orenda.country.entity.User;
import com.orenda.country.exception.AppException;
import com.orenda.country.exception.ErrorCode;
import com.orenda.country.repository.UserRepository;
import com.orenda.country.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;



    @Override
    public String login(LoginRequest request){
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));
        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }
        Map<String,Object> extraClaims = new HashMap<>();
        extraClaims.put(JwtClaimNames.USER_ID, user.getId());
        extraClaims.put(JwtClaimNames.FULL_NAME, user.getUsername());
        return jwtService.generateToken(extraClaims, user);
    }

    @Override
    public UserResponse createUser(UserRequest request){

    }
}
