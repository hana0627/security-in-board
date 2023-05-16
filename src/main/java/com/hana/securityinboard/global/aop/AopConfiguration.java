package com.hana.securityinboard.global.aop;

import com.hana.securityinboard.application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AopConfiguration {

    private final UserRepository userRepository;
    @Bean
    public UserBlockCheckAspect timeCheckAspect() {
        return new UserBlockCheckAspect(userRepository);
    }

}
