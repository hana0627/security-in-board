package com.hana.securityinboard.application.controller;

import com.hana.securityinboard.application.dto.UserAccountDto;
import com.hana.securityinboard.application.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/login")
@Controller
public class LoginController {
    private final UserService userService;
    @GetMapping("")
    public String loginForm() {
        return "/user/loginForm";
    }
    @GetMapping("/join")
    public String joinForm() {
        // 따로 valid 처리안함.
        return "/user/joinForm";
    }

    @PostMapping("/join")
    public String join(UserAccountDto userAccountDto) {
        log.info("[UserController - join]");
        userService.saveUser(userAccountDto);
        return "redirect:/home";
    }
}
