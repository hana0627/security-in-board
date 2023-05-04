package com.hana.securityinboard.application.controller;

import com.hana.securityinboard.application.dto.UserAccountDto;
import com.hana.securityinboard.application.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;
    @GetMapping("/user/join")
    public String joinForm() {
        // 따로 valid 처리안함.
        return "/user/loginForm";
    }
    @PostMapping("/user/join")
    public String join(UserAccountDto userAccountDto) {
        log.info("[UserController - join]");
        userService.saveUser(userAccountDto);
        return "redirect:/home";
    }
    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
