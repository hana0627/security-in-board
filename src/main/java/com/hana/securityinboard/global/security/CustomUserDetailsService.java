package com.hana.securityinboard.global.security;

import com.hana.securityinboard.application.domain.UserAccount;
import com.hana.securityinboard.application.service.UserService;
import com.hana.securityinboard.global.util.LoginCounter;
import com.hana.securityinboard.global.util.LoginCounterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;
    private final LoginCounterRepository loginCounterRepository;
    /**
     * 이곳에서 마지막 로그인 일자와 ip를 기록
     */
    @Transactional
    public CustomUserDetails customLoadUserByUsername(String username, String remoteAddress) throws RuntimeException {
        return userService.searchUser(username).map(
                u -> {
                    if(u.loginDayCounter()) {
                        // 오늘동안 처음 접속한 회원이면 count 수를 올려준다.
                        LoginCounter loginCounter = loginCounterRepository.findById(1L).get();
                        loginCounter.loginCount();
                    };
                    u.setLastLoginIp(remoteAddress);
                    return new CustomUserDetails(u);
                }
        ).orElseThrow(() ->new UsernameNotFoundException("회원이 존재하지 않습니다!"));
    }
    
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAccount> userAccount = userService.searchUser(username);

        return userAccount.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("회원이 존재하지 않습니다!"));
    }
}
