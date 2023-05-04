package com.hana.securityinboard.global.security;

import com.hana.securityinboard.application.domain.UserAccount;
import com.hana.securityinboard.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.print.event.PrintEvent;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;
    private List<UserDetails> userDetailsList;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAccount> userAccount = userService.searchUser(username);

        return userAccount.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("회원이 존재하지 않습니다!"));
    }
}
