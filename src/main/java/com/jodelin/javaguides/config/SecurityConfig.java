package com.jodelin.javaguides.config;

import com.jodelin.javaguides.services.CustumUserDetailsService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
@SecurityScheme(name = "Bear authentication",type = SecuritySchemeType.HTTP,bearerFormat = "JWT",scheme = "bearer")
public class SecurityConfig {
    private  final CustumUserDetailsService userDetailsService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return  configuration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(crsf->crsf.disable());
        http.userDetailsService(userDetailsService);
        http.authorizeHttpRequests(request->{
            request.requestMatchers(HttpMethod.GET,"/api/**").permitAll();
            request.requestMatchers("/api/auth/**").permitAll();
            request.requestMatchers("/swagger-ui/**").permitAll();
            request.requestMatchers("/v3/api-docs/**").permitAll();
        });

        http.authorizeHttpRequests(request->{
           request.anyRequest().authenticated();
        });


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails user= User.builder().
//                        username("user").
//                        password(passwordEncoder().encode("password")).
//                        roles("USER").build();
//
//        UserDetails admin= User.builder().
//                username("admin").
//                password(passwordEncoder().encode("password")).
//                roles("ADMIN").build();
//
//
//        return new InMemoryUserDetailsManager(user,admin);
//    }
}
