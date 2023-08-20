package com.jodelin.javaguides.services;

import com.jodelin.javaguides.entities.User;
import com.jodelin.javaguides.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class CustumUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        User user= userRepository.
                findByUsernameOrEmail(usernameOrEmail,usernameOrEmail)
                .orElseThrow(
                        ()->new UsernameNotFoundException("user not found "+usernameOrEmail));
        Set<GrantedAuthority> authorities= user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }
}
