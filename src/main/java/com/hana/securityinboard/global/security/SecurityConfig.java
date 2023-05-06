package com.hana.securityinboard.global.security;

import com.hana.securityinboard.application.service.UserService;
import com.hana.securityinboard.global.security.oauth.CustomOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CustomOauth2UserService oauth2UserService;
    private final CustomUserDetailsService userDetailsService;
    private final UserService userService;
    private final CustomAuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return
                http
                        .csrf(c-> c.disable())
                        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .authorizeHttpRequests(auth -> auth
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers("/login/**").permitAll()
                                .anyRequest().authenticated()
                                )
                        .formLogin(form -> {
                            form.loginPage("/login");
                            form.successHandler(new CustomAuthenticationSuccessHandler(userDetailsService));
                            form.successForwardUrl("/home");
                        })
                        .oauth2Login(oauth -> {
                            oauth.loginPage("/login");
                            oauth.userInfoEndpoint().userService(oauth2UserService);
                            oauth.successHandler(new CustomAuthenticationSuccessHandler(userDetailsService));
                        })
//                        .addFilterBefore(new JwtAuthenticationFilter(userDetailsService, userService), UsernamePasswordAuthenticationFilter.class)
                        .build();
    }

}
