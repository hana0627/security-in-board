package com.hana.securityinboard.global.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hana.securityinboard.application.domain.UserAccount;
import com.hana.securityinboard.application.repository.UserRepository;
import com.hana.securityinboard.application.service.UserService;
import com.hana.securityinboard.global.security.CustomUserDetails;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component //추가
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final UserService userService;
    @Value("${jwt.header}")
    private String jwtHeader;
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.token-validity-in-seconds}")
    private String jwtRestTime;
    @Value("${jwt.token_prefix}")
    private String jwtTokenPrefix;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("jwtHeader = {}", jwtHeader);
        log.info("jwtSecret = {}", jwtSecret);
        log.info("jwtRestTime = {}", jwtRestTime);
        log.info("jwtTokenPrefix = {}", jwtTokenPrefix);

        String jwtHeaderGet = request.getHeader(jwtHeader);
        // 헤더가 있는지 확인
        if(jwtHeaderGet == null || !jwtHeaderGet.startsWith(jwtTokenPrefix)) {
            chain.doFilter(request, response);
            return;
        }

        String jwtToken = jwtHeaderGet.replace(jwtTokenPrefix, "");

        // 유저정보 확인
        String username =
                JWT.require(Algorithm.HMAC512(jwtSecret)).build().verify(jwtToken).getClaim("username").asString();

        //
        if (username != null) {
            Optional<UserAccount> userAccount = userService.searchUser(username);
            UserAccount user = userAccount.get();

            CustomUserDetails principalDetails = new CustomUserDetails(user);

            // jwt토큰 서명을 통해서 서명이 정상이면 Authentication 객체 생성.
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
            // 강제로 시큐리티 세션에 접근하여 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);
        }
        chain.doFilter(request, response);
    }
}
