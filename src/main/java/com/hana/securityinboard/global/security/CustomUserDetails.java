package com.hana.securityinboard.global.security;

import com.hana.securityinboard.application.domain.UserAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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


    //TODO : 제대로 들어가는지 확인
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userAccount.getRoleType().getRoleName()));
        return authorities;
//        System.out.println("현재 여기를 못들어옴");
//        Collection<GrantedAuthority> collect = new ArrayList<>();
//        collect.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return userAccount.getRoleType().getRoleName();
//            }
//        });
//        return collect;
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
