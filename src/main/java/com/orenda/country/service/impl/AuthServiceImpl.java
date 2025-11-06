package com.orenda.country.service.impl;

import com.orenda.country.common.security.jwt.JwtClaimNames;
import com.orenda.country.common.security.jwt.JwtService;
import com.orenda.country.dto.auth.LoginRequest;
import com.orenda.country.dto.auth.LoginResponse;
import com.orenda.country.dto.request.UserRequest;
import com.orenda.country.dto.response.UserResponse;
import com.orenda.country.entity.*;
import com.orenda.country.enums.Role;
import com.orenda.country.exception.AppException;
import com.orenda.country.exception.ErrorCode;
import com.orenda.country.mapper.UserMapper;
import com.orenda.country.repository.RolePermissionRepository;
import com.orenda.country.repository.RoleRepository;
import com.orenda.country.repository.UserRepository;
import com.orenda.country.repository.UserRoleRepository;
import com.orenda.country.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public String login(LoginRequest request){
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));
        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }
        List<String> namePermission = roleRepository.namePermission(user.getRole().name());
        Map<String,Object> extraClaims = new HashMap<>();
        extraClaims.put(JwtClaimNames.USER_ID, user.getId());
        extraClaims.put(JwtClaimNames.FULL_NAME, user.getUsername());
        extraClaims.put(JwtClaimNames.ROLE, namePermission);
        return jwtService.generateToken(extraClaims, user);
    }

    @Override
    public UserResponse createUser(UserRequest request){
        boolean username = userRepository.existsByUsername(request.getUsername());
        if(username){
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        Roles role = roleRepository.findByName(user.getRole().name()).orElseThrow(()-> new AppException(ErrorCode.ROLE_NOT_ROUND));
        UserRole userRole = new UserRole();
        userRole.setId(new UserRoleKey(user.getId(), role.getId()));
        userRoleRepository.save(userRole);
        return userMapper.toUserResponse(user);
    }
}
