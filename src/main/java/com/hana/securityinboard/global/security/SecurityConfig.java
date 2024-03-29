package com.hana.securityinboard.global.security;

import com.hana.securityinboard.application.domain.constant.RoleTypes;
import com.hana.securityinboard.application.service.UserService;
import com.hana.securityinboard.global.security.oauth.CustomOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CustomOauth2UserService oauth2UserService;
    private final CustomUserDetailsService userDetailsService;
    private final UserService userService;
    private final CustomAuthenticationProvider authenticationProvider;

//    String[] roleType1 = {
//            RoleType.SILVER.getRoleName(),
//            RoleType.ORANGE.getRoleName(),
//            RoleType.RED.getRoleName(),
//            RoleType.VIP.getRoleName(),
//            RoleType.MANAGER.getRoleName(),
//            RoleType.ADMIN.getRoleName()
//    };
//    String[] roleType2 = {
//            RoleType.ORANGE.getRoleName(),
//            RoleType.RED.getRoleName(),
//            RoleType.VIP.getRoleName(),
//            RoleType.MANAGER.getRoleName(),
//            RoleType.ADMIN.getRoleName()
//    };
//    String[] roleType3 = {
//            RoleType.RED.getRoleName(),
//            RoleType.VIP.getRoleName(),
//            RoleType.MANAGER.getRoleName(),
//            RoleType.ADMIN.getRoleName()
//    };
//    String[] roleType4 = {
//            RoleType.VIP.getRoleName(),
//            RoleType.MANAGER.getRoleName(),
//            RoleType.ADMIN.getRoleName()
//    };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return
                http
//                        .csrf(c -> c.disable())
                        .csrf(c -> c.ignoringRequestMatchers("/login"))
                        .sessionManagement(s -> s
                                .maximumSessions(1)
                                .maxSessionsPreventsLogin(true)
                                .sessionRegistry(sessionRegistry())
                        )
//                        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .authorizeHttpRequests(auth -> auth
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers("/login/**","/home/**").permitAll()
                                .requestMatchers("/article/a/**").hasAnyRole(RoleTypes.roleType1)
                                .requestMatchers("/article/b/**").hasAnyRole(RoleTypes.roleType2)
                                .requestMatchers("/article/c/**").hasAnyRole(RoleTypes.roleType3)
                                .requestMatchers("/article/d/**").hasAnyRole(RoleTypes.roleType4)
//                                .requestMatchers(HttpMethod.GET,"/article/a/**").hasAnyRole(RoleTypes.roleType1)
//                                .requestMatchers(HttpMethod.POST,"/article/a/**").hasAnyRole(RoleTypes.roleType2)
//                                .requestMatchers(HttpMethod.GET,"/article/b/**").hasAnyRole(RoleTypes.roleType2)
//                                .requestMatchers(HttpMethod.POST,"/article/b/**").hasAnyRole(RoleTypes.roleType3)
//                                .requestMatchers(HttpMethod.GET,"/article/c/**").hasAnyRole(RoleTypes.roleType3)
//                                .requestMatchers(HttpMethod.POST,"/article/c/**").hasAnyRole(RoleTypes.roleType4)
//                                .requestMatchers(HttpMethod.GET,"/article/d/**").hasAnyRole(RoleTypes.roleType4)
//                                .requestMatchers(HttpMethod.POST,"/article/d/**").hasAnyRole(RoleTypes.roleType5)
                                .anyRequest().authenticated()
                        )
                        .formLogin(form -> {
                            form.loginPage("/login");
                            form.successHandler(customAuthenticationSuccessHandler());
                            form.successHandler(customAuthenticationSuccessHandler());
//                            form.successForwardUrl("/home");
                        })
                        .oauth2Login(oauth -> {
                            oauth.loginPage("/login");
                            oauth.userInfoEndpoint().userService(oauth2UserService);
                            oauth.successHandler(customAuthenticationSuccessHandler());
                            oauth.defaultSuccessUrl("/home");
                        })
//                        .addFilterBefore(new JwtAuthenticationFilter(userDetailsService, userService), UsernamePasswordAuthenticationFilter.class)
                        .build();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler(userDetailsService);
    }

}
