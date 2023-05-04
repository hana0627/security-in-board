package com.hana.securityinboard.application.service;

import com.hana.securityinboard.application.domain.UserAccount;
import com.hana.securityinboard.application.dto.UserAccountDto;
import com.hana.securityinboard.application.repository.UserRepository;
import com.hana.securityinboard.global.security.CustomPasswordEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {
    private final CustomPasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public Optional<UserAccount> searchUser(String username) {
        return userRepository.findByUsername(username);
    }


    //TODO : 유저이름 중복검증 좀 더 생각
    @Transactional
    public void saveUser(UserAccountDto userAccountDto) {
        log.info("[UserService saveUser]");
        if (userRepository.findByUsername(userAccountDto.username()).isPresent()) {
            throw new IllegalStateException("로그인 아이디는 중복될 수 없습니다!");
        }
        userRepository.save(userAccountDto.toEntity(passwordEncoder));


    }
}
