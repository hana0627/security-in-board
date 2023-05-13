package com.hana.securityinboard.global.security;

import com.hana.securityinboard.application.domain.UserAccount;
import com.hana.securityinboard.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;
    private List<CustomUserDetails> userDetailsList;

    /**
     * 커스텀. 이곳에서 마지막 로그인 일자와 ip를 기록
     */
    @Transactional
    public CustomUserDetails customLoadUserByUsername(String username, String remoteAddress) throws RuntimeException {
        return userService.searchUser(username).map(
                u -> {
                    u.loginDayCounter();
                    u.setLastLoginIp(remoteAddress);
                    return new CustomUserDetails(u);
                }
        ).orElseThrow(() ->new UsernameNotFoundException("회원이 존재하지 않습니다!"));

//        UserAccount user = userService.searchUser(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//        new User(
//                user.getUsername(),
//                user.getPassword(),
//                user.getRoleType());
//        return new CustomUserDetails(user);
    }
    
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAccount> userAccount = userService.searchUser(username);

        return userAccount.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("회원이 존재하지 않습니다!"));
    }
}
