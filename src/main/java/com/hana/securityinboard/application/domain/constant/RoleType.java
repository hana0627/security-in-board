package com.hana.securityinboard.application.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

@RequiredArgsConstructor
public enum RoleType {
//    SILVER("ROLE_SILVER", "실버등급"),
//    ORANGE("ROLE_ORANGE", "오렌지등급"),
//    RED("ROLE_RED", "레드등급"),
//    VIP("ROLE_VIP", "VIP"),
//    MANAGER("ROLE_MANAGER", "운영자"),
//    ADMIN("ROLE_ADMIN", "관리자");
    SILVER("SILVER", "실버등급"),
    ORANGE("ORANGE", "오렌지등급"),
    RED("RED", "레드등급"),
    VIP("VIP", "VIP"),
    MANAGER("MANAGER", "운영자"),
    ADMIN("ADMIN", "관리자");

    @Getter
    private final String roleName;
    @Getter
    private final String description;


}
