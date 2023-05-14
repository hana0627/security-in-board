package com.hana.securityinboard.application.domain.constant;

import com.hana.securityinboard.application.domain.UserAccount;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

@RequiredArgsConstructor
public enum RoleType {
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

    public static RoleType upgradeRole(RoleType roleType) {
        return switch (roleType) {
            case SILVER -> ORANGE;
            case ORANGE -> RED;
            case RED -> VIP;
            default -> roleType;
        };
    }

    public static boolean canCreateArticle(String role, String board) {
        return switch (board) {
            case "A" -> Arrays.stream(RoleTypes.roleType2).anyMatch(r -> role.equals(r));
            case "B" -> Arrays.stream(RoleTypes.roleType3).anyMatch(r -> role.equals(r));
            case "C" -> Arrays.stream(RoleTypes.roleType4).anyMatch(r -> role.equals(r));
            case "D" -> Arrays.stream(RoleTypes.roleType5).anyMatch(r -> role.equals(r));
            default -> false;
        };
    }

    public static boolean canDeleteArticle(String role, String board) {
        System.out.println("???" + role);
        return switch (board) {
            case "A" -> Arrays.stream(RoleTypes.roleType3).anyMatch(r -> role.equals(r));
            case "B" -> Arrays.stream(RoleTypes.roleType4).anyMatch(r -> role.equals(r));
            default -> false;
        };
    }
}
