package com.hana.securityinboard.application.domain.constant;

public class RoleTypes {

    public static String[] roleType1 = {
            RoleType.SILVER.getRoleName(),
            RoleType.ORANGE.getRoleName(),
            RoleType.RED.getRoleName(),
            RoleType.VIP.getRoleName(),
            RoleType.MANAGER.getRoleName(),
            RoleType.ADMIN.getRoleName()
    };
    public static String[] roleType2 = {
            RoleType.ORANGE.getRoleName(),
            RoleType.RED.getRoleName(),
            RoleType.VIP.getRoleName(),
            RoleType.MANAGER.getRoleName(),
            RoleType.ADMIN.getRoleName()
    };
    public static String[] roleType3 = {
            RoleType.RED.getRoleName(),
            RoleType.VIP.getRoleName(),
            RoleType.MANAGER.getRoleName(),
            RoleType.ADMIN.getRoleName()
    };
    public static String[] roleType4 = {
            RoleType.VIP.getRoleName(),
            RoleType.MANAGER.getRoleName(),
            RoleType.ADMIN.getRoleName()
    };
}
