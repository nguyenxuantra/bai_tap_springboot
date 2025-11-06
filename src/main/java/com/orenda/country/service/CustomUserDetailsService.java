package com.orenda.country.service;

import com.orenda.country.enums.Role;
import com.orenda.country.repository.RoleRepository;
import com.orenda.country.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        var userDetails = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"+ username));
        List<String> namePermissions = roleRepository.namePermission(userDetails.getRole().name());

        userDetails.setPermission(namePermissions);
        return  userDetails;
    }
}
