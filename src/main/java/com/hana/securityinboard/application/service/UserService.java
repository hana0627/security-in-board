package com.hana.securityinboard.application.service;

import com.hana.securityinboard.application.domain.UserAccount;
import com.hana.securityinboard.application.domain.constant.RoleType;
import com.hana.securityinboard.application.dto.UserAccountDto;
import com.hana.securityinboard.application.repository.UserRepository;
import com.hana.securityinboard.global.security.CustomPasswordEncoder;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public List<UserAccount> searchUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void saveUser(UserAccountDto userAccountDto) {
        log.info("[UserService saveUser]");
        if (userRepository.findByUsername(userAccountDto.username()).isPresent()) {
            throw new IllegalStateException("로그인 아이디는 중복될 수 없습니다!");
        }
        userRepository.save(userAccountDto.toEntity(passwordEncoder));
    }

    @Transactional
    public UserAccount saveUser(UserAccount userAccount) {
        log.info("[UserService saveUser]");
        if (userRepository.findByUsername(userAccount.getUsername()).isPresent()) {
            throw new IllegalStateException("로그인 아이디는 중복될 수 없습니다!");
        }
        return userRepository.save(userAccount);
    }

    @Transactional
    public boolean upgradeRole(Authentication auth) {
        UserAccount user = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다!"));

        if (user.canUpgrade(user)) {
            user.upgradeRole(user);
        }
        return user.canUpgrade(user);
    }

    public boolean isAdmin(Authentication auth) {
        UserAccount userAccount = userRepository.findByUsername(auth.getName()).orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다!"));
        return userAccount.getRoleType().equals(RoleType.ADMIN);
    }

    public List<UserAccountDto> searchAllUser() {
        // 페이징은 고려하지 않았음
        return userRepository.findAll().stream().map(UserAccountDto::form).toList();
    }
}
