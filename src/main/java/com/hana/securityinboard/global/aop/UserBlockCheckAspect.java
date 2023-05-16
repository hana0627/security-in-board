package com.hana.securityinboard.global.aop;

import com.hana.securityinboard.application.domain.UserAccount;
import com.hana.securityinboard.application.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
@RequiredArgsConstructor
@Aspect
public class UserBlockCheckAspect {
    private final UserRepository userRepository;


    @Around("@annotation(com.hana.securityinboard.global.aop.annotation.UserIsBlocked)")
    public Object userIsBlocked(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[UserBlockCheckAspect userIsBlocked] - called");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserAccount user = userRepository.findByUsername(auth.getName()).orElseThrow(() -> new EntityNotFoundException());
        if (!user.isBlock(user)) {
            throw new AccessDeniedException("차단된 유저는 접근할 수 없습니다!");
        }
        return joinPoint.proceed();
    }

}
