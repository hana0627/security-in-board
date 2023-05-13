package com.hana.securityinboard.global.security;

//import com.hana.securityinboard.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
        import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomPasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;


    @Value("${jwt.header}")
    String jwtHeader;
    @Value("${jwt.secret}")
    String jwtSecret;
    @Value("${jwt.token-validity-in-seconds}")
    String jwtRestTime;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        log.info("[CustomAuthenticationProvider] authentication : {}", authentication);
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        String remoteAddress = details.getRemoteAddress();
        CustomUserDetails u = userDetailsService.customLoadUserByUsername(username, remoteAddress);
        if(passwordEncoder.matches(password,u.getPassword())) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(u.getUserAccount().getRoleType().getRoleName()));
            return new UsernamePasswordAuthenticationToken(username, password, authorities);
        }
        throw new BadCredentialsException("Something was wrong");


    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}

//        일단 권한정보가 제대로 들어가게끔 수정은 하였으나,
//        문제를 해결한것이지, 어떻게 해결하였는지 정확하게 이해하지 않고있다.
//        일단 아래 주석으로 남겨둔 후 비교하도록 하자
//        log.info("[CustomAuthenticationProvider] authentication : {}", authentication);
//        String username = authentication.getName();
//        String password = authentication.getCredentials().toString();
//        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
//        String remoteAddress = details.getRemoteAddress();
//        CustomUserDetails u = userDetailsService.customLoadUserByUsername(username, remoteAddress);
//        if(passwordEncoder.matches(password,u.getPassword())) {
//            return new UsernamePasswordAuthenticationToken(username, password, authentication.getAuthorities());
//        }
//        throw new BadCredentialsException("Something was wrong");


