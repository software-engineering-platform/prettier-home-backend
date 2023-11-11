package com.ph.security.config;

import com.ph.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
@Configuration
@RequiredArgsConstructor
public class UserDetailsConfig {

    private final UserService userService;


    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userService.getUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found for authentication"));
    }

}
