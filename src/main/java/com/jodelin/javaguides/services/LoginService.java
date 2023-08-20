package com.jodelin.javaguides.services;

import com.jodelin.javaguides.dtos.LoginDto;
import com.jodelin.javaguides.dtos.RegisterUserDto;
import com.jodelin.javaguides.entities.Role;
import com.jodelin.javaguides.entities.User;
import com.jodelin.javaguides.exceptions.BlogAPIException;
import com.jodelin.javaguides.repositories.RoleRepository;
import com.jodelin.javaguides.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public String loginUser(LoginDto loginDto){
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return "user login successfully";
    }

    public String registerUser(RegisterUserDto registerUserDto) {

        if(userRepository.existsByEmail(registerUserDto.getEmail())){
            throw  new BlogAPIException(HttpStatus.BAD_REQUEST,"email is alredy existed");
        }

        if(userRepository.existsByUsername(registerUserDto.getUsername())){
            throw  new BlogAPIException(HttpStatus.BAD_REQUEST,"username is alredy existed");
        }

        User user= new User();
        user.setName(registerUserDto.getName());
        user.setUsername(registerUserDto.getUsername());
        user.setEmail(registerUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));

        Set<Role> roles= new HashSet<>();
        roles.add(roleRepository.findByRole("ROLE_ADMIN").get());
        user.setRoles(roles);
        userRepository.save(user);
        return "user saved";
    }
}
