package com.hana.securityinboard.application.domain.constant;

import jakarta.persistence.AttributeConverter;

import java.util.Arrays;

public class RoleTypesConverter implements AttributeConverter<RoleType, String> {

    @Override
    public String convertToDatabaseColumn(RoleType attribute) {
        return attribute.getRoleName();
    }
    @Override
    public RoleType convertToEntityAttribute(String dbData) {
        return Arrays.stream(RoleType.values())
                .filter(s -> s.getRoleName().equals(dbData))
                .findFirst().orElseThrow();
    }
}
