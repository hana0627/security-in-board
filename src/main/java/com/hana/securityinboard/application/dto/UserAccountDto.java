package com.hana.securityinboard.application.dto;

import com.hana.securityinboard.application.domain.UserAccount;
import com.hana.securityinboard.application.domain.constant.RoleType;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

public record UserAccountDto(
        String username,
        String password,
        String email,
        String name,
        LocalDateTime accountDate,
        LocalDateTime lastLoginDate,
        String lastLoginIp,
        RoleType roleType,
        Integer loginDay,
        Integer articleCount

) {

    public static UserAccountDto of(String username, String password, String email, String name, LocalDateTime accountDate, LocalDateTime lastLoginDate, String lastLoginIp, RoleType roleType, Integer loginDay, Integer articleCount) {
        return new UserAccountDto(username, password, email, name, accountDate, lastLoginDate, lastLoginIp, roleType, loginDay, articleCount);
    }

    public static UserAccountDto form (UserAccount userAccount) {
        return new UserAccountDto(
                userAccount.getUsername(),
                userAccount.getPassword(),
                userAccount.getEmail(),
                userAccount.getName(),
                userAccount.getAccountDate(),
                userAccount.getLastLoginDate(),
                userAccount.getLastLoginIp(),
                userAccount.getRoleType(),
                userAccount.getLoginDay(),
                userAccount.getArticleCount()
        );
    }

    //TODO : 현재는 회원가입용으로만 생각하고 구현하였음
    public UserAccount toEntity(PasswordEncoder passwordEncoder) {
        return UserAccount.of(
                username,
                passwordEncoder.encode(password), //저장시 암호화 수행
                email,
                name,
                LocalDateTime.now(),
                LocalDateTime.now(),
                lastLoginIp,
                RoleType.SILVER,
                1,
                0
        );
    }
    public UserAccount toEntity() {
        return UserAccount.of(
                username,
                password, //저장시 암호화 수행
                email,
                name,
                accountDate,
                lastLoginDate,
                lastLoginIp,
                roleType,
                loginDay,
                articleCount
        );
    }


    public UserAccountDto(String username, String password, String email, String name, LocalDateTime accountDate, LocalDateTime lastLoginDate, String lastLoginIp, RoleType roleType, Integer loginDay, Integer articleCount) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.accountDate = accountDate;
        this.lastLoginDate = lastLoginDate;
        this.lastLoginIp = lastLoginIp;
        this.roleType = roleType;
        this.loginDay = loginDay;
        this.articleCount = articleCount;
    }
}
