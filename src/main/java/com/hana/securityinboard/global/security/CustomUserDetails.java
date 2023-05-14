package com.hana.securityinboard.global.security;

import com.hana.securityinboard.application.domain.UserAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

public class CustomUserDetails implements UserDetails, OAuth2User {

    private UserAccount userAccount;
    private Map<String, Object> attributes;

    // form 로그인
    public CustomUserDetails(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    //Oauth 로그인
    public CustomUserDetails(UserAccount userAccount, Map<String, Object> attributes) {
        this.userAccount = userAccount;
        this.attributes = attributes;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    @Override
    public String getUsername() {
        return userAccount.getUsername();
    }

    @Override
    public String getPassword() {
        return userAccount.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.singletonList(new CustomGrantedAuthority(userAccount.getRoleType().getRoleName()));
        return Collections.emptyList();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    //Oauth2
    @Override
    public String getName() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
