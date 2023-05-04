package com.hana.securityinboard.global.security.oauth;

import com.hana.securityinboard.application.domain.UserAccount;
import com.hana.securityinboard.application.domain.constant.RoleType;
import com.hana.securityinboard.application.repository.UserRepository;
import com.hana.securityinboard.application.service.UserService;
import com.hana.securityinboard.global.security.CustomPasswordEncoder;
import com.hana.securityinboard.global.security.CustomUserDetails;
import com.hana.securityinboard.global.security.oauth.provider.Oauth2UserInfo;
import com.hana.securityinboard.global.security.oauth.provider.impl.GoogleUserInfo;
import com.hana.securityinboard.global.security.oauth.provider.impl.KakaoUserInfo;
import com.hana.securityinboard.global.security.oauth.provider.impl.NaverUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CustomOauth2UserService extends DefaultOAuth2UserService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final CustomPasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {


        OAuth2User oauth2User = super.loadUser(userRequest);


        Oauth2UserInfo oauth2UserInfo = null;

        System.out.println("여기확인");
        System.out.println(oauth2User.getAttributes());
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oauth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            oauth2UserInfo = new NaverUserInfo((Map) oauth2User.getAttributes().get("response"));
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            oauth2UserInfo = new KakaoUserInfo((Map) oauth2User.getAttributes());
        }


        String provider = oauth2UserInfo.getProvider();
        String providerId = oauth2UserInfo.getProviderId();
        String username = provider + "_" + providerId;
        String email = oauth2UserInfo.getEmail(); 
        String password = passwordEncoder.encode("{bcrypt}dummy" + UUID.randomUUID());
        String name = oauth2UserInfo.getName(); //구글 이상하게 찍힘google_100025900526046763060
        RoleType role = RoleType.USER;


        //searchUser 해서 값이 있으면(이미 한번이상 로그인한 회원) 바로 return, 없으면 객체를 생성해서 return
        return userService.searchUser(username)
                .map(userAccount -> new CustomUserDetails(userAccount, oauth2User.getAttributes()))
                .orElseGet(() -> {
                    UserAccount savedUser = userRepository.save(UserAccount.of(username, password, email, name, role));
                    return new CustomUserDetails(savedUser, oauth2User.getAttributes());
                });
    }
}
