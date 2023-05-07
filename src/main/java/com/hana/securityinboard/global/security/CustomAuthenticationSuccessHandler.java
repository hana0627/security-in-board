package com.hana.securityinboard.global.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import java.io.IOException;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
//@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final CustomUserDetailsService userDetailsService;
    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStratgy = new DefaultRedirectStrategy();
    @Value("${jwt.header}")
    private String jwtHeader;
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.token-validity-in-seconds}")
    private String jwtRestTime;
    @Value("${jwt.token_prefix}")
    private String jwtTokenPrefix;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        if (authentication.isAuthenticated()) {
            log.info("인증성공!");
            CustomUserDetails u = userDetailsService.loadUserByUsername(authentication.getName());
            // 비밀번호가 같다면 jwt 토큰 생성
            String token = JWT.create()
                    .withSubject(u.getUsername())// 토큰이름
                    .withExpiresAt(new Date(System.currentTimeMillis() + Integer.parseInt(jwtRestTime))) // 토큰 지속시간
                    .withExpiresAt(new Date(System.currentTimeMillis() + 86400)) // 토큰 지속시간
                    // payLoad에 들어갈 내용
                    .withClaim("id", u.getUserAccount().getId())
                    .withClaim("username", u.getUserAccount().getUsername())
                    .withClaim("auth", u.getUserAccount().getRoleType().getRoleName())
                    .sign(Algorithm.HMAC512("mySecretKey"));

            log.info("Authorization : {}", "Bearer " + token);
            response.addHeader("Authorization", "Bearer " + token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
            response.setHeader("Location", "/home");
        }
    }
}
