package com.hana.securityinboard.global.security.oauth;

import com.hana.securityinboard.application.domain.UserAccount;
import com.hana.securityinboard.application.domain.constant.RoleType;
import com.hana.securityinboard.application.service.UserService;
import com.hana.securityinboard.global.security.CustomPasswordEncoder;
import com.hana.securityinboard.global.security.CustomUserDetails;
import com.hana.securityinboard.global.security.oauth.provider.Oauth2UserInfo;
import com.hana.securityinboard.global.security.oauth.provider.impl.GoogleUserInfo;
import com.hana.securityinboard.global.security.oauth.provider.impl.KakaoUserInfo;
import com.hana.securityinboard.global.security.oauth.provider.impl.NaverUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOauth2UserService extends DefaultOAuth2UserService {
    private final UserService userService;
    private final CustomPasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {


        OAuth2User oauth2User = super.loadUser(userRequest);

        Oauth2UserInfo oauth2UserInfo = null;

        log.info("oauth2User.getAttributes()");

//        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
        if (("google").equals(userRequest.getClientRegistration().getRegistrationId())) {
            oauth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());
//        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
        } else if (("naver").equals(userRequest.getClientRegistration().getRegistrationId())) {
            oauth2UserInfo = new NaverUserInfo((Map) oauth2User.getAttributes().get("response"));
        } else if (("kakao").equals(userRequest.getClientRegistration().getRegistrationId())) {
            oauth2UserInfo = new KakaoUserInfo((Map) oauth2User.getAttributes());
        }


        String provider = oauth2UserInfo.getProvider();
        String providerId = oauth2UserInfo.getProviderId();
        String username = provider + "_" + providerId;
        String email = oauth2UserInfo.getEmail();
        String password = passwordEncoder.encode("{bcrypt}dummy" + UUID.randomUUID());
        String name = oauth2UserInfo.getName();
        RoleType role = RoleType.SILVER;


        //searchUser 해서 값이 있으면(이미 한번이상 로그인한 회원) 바로 return, 없으면 객체를 생성해서 return
        CustomUserDetails customUserDetails = userService.searchUser(username)
                .map(userAccount -> new CustomUserDetails(userAccount, oauth2User.getAttributes()))
                .orElseGet(() -> {
                    UserAccount savedUser = userService.saveUser(UserAccount.of(username, password, email, name, role));
                    return new CustomUserDetails(savedUser, oauth2User.getAttributes());
                });



        // create authorities
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(customUserDetails.getUserAccount().getRoleType().getRoleName()));

        // create authentication
//        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, token, issuedAt, expiresAt);
//        OAuth2AuthenticationToken authenticationToken = new OAuth2AuthenticationToken(
//                customUserDetails, authorities, userRequest.getClientRegistration().getRegistrationId(), accessToken);

        return customUserDetails;

    }

}
