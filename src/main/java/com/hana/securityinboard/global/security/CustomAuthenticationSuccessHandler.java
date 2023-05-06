package com.hana.securityinboard.global.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final CustomUserDetailsService userDetailsService;
    @Value("${jwt.header}")
    private String jwtHeader;
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.token-validity-in-seconds}")
    private String jwtRestTime;
    @Value("${jwt.token_prefix}")
    private String jwtTokenPrefix;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUserDetails u = userDetailsService.loadUserByUsername(authentication.getName());
        //비밀번호가 같다면 jwt 토큰 생성
        String token = JWT.create()
                .withSubject(u.getUsername())// 토큰이름
//                .withExpiresAt(new Date(System.currentTimeMillis() + Integer.parseInt(jwtRestTime))) // 토큰 지속시간
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400)) // 토큰 지속시간
                //payLoad에 들어갈 내용
                .withClaim("id", u.getUserAccount().getId())
                .withClaim("username", u.getUserAccount().getUsername())
                .withClaim("auth", u.getUserAccount().getRoleType().getRoleName())
                .sign(Algorithm.HMAC512("mySecretKey"));

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        response.addHeader("Authorization","Bearer " +token);
        response.sendRedirect("/home");
    }
}
