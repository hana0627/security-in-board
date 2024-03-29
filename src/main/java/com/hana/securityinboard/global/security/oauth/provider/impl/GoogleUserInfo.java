package com.hana.securityinboard.global.security.oauth.provider.impl;

import com.hana.securityinboard.global.security.oauth.provider.Oauth2UserInfo;

import java.util.Map;

public class GoogleUserInfo implements Oauth2UserInfo {

    private Map<String, Object> attributes;

    public GoogleUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }
}
