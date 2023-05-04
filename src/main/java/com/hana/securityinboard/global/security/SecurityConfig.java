package com.hana.securityinboard.global.security;

import com.hana.securityinboard.global.security.oauth.CustomOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final CustomOauth2UserService oauth2UserService;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return
                http
                        .csrf(c-> c.disable())
                        .authorizeHttpRequests(auth -> auth
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers("/login/**").permitAll()
                                .anyRequest().authenticated()
                                )
                        .formLogin(form -> {
                            form.loginPage("/login");
                            form.successForwardUrl("/home");
                        })
                        .oauth2Login(oauth -> {
                            oauth.loginPage("/login");
                            oauth.userInfoEndpoint().userService(oauth2UserService);
                            oauth.successHandler((request, response, authentication) -> response.sendRedirect("/home"));
                        })
                        .build();
    }
}
