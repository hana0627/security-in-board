package com.hana.securityinboard.global.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomPasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("[CustomAuthenticationProvider] authentication : {}", authentication);
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails u = userDetailsService.loadUserByUsername(username);
        if(passwordEncoder.matches(password,u.getPassword())) {
            return new UsernamePasswordAuthenticationToken(username, password, authentication.getAuthorities());
        }
        throw new BadCredentialsException("Something was wrong");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
