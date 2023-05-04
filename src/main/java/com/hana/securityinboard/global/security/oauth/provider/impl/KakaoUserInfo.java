package com.hana.securityinboard.global.security.oauth.provider.impl;

import com.hana.securityinboard.global.security.oauth.provider.Oauth2UserInfo;

import java.util.Map;

public class KakaoUserInfo implements Oauth2UserInfo {
    private Map<String, Object> attributes;

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    //TODO : 동의한거 같은데 값이 안넘어오는 이슈 발생
    @Override
    public String getEmail() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        if (kakaoAccount != null && kakaoAccount.containsKey("has_email") && kakaoAccount.containsKey("email_needs_agreement")) {
            boolean hasEmail = (Boolean) kakaoAccount.get("has_email");
            boolean emailNeedsAgreement = (Boolean) kakaoAccount.get("email_needs_agreement");
            if (hasEmail && emailNeedsAgreement) {
                return (String) kakaoAccount.get("email");
            }
        }
        return null;

    }

    @Override
    public String getName() {
        Object kakaoAccount = attributes.get("kakao_account");
        if (kakaoAccount instanceof Map) {
            Map<String, Object> kakaoAccountMap = (Map<String, Object>) kakaoAccount;
            Object profile = kakaoAccountMap.get("profile");
            if (profile instanceof Map) {
                Map<String, Object> profileMap = (Map<String, Object>) profile;
                Object nickname = profileMap.get("nickname");
                if (nickname instanceof String) {
                    return (String) nickname;
                }
            }
        }
        return null;
    }
}
