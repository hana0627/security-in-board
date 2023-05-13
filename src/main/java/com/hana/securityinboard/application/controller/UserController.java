package com.hana.securityinboard.application.controller;

import com.hana.securityinboard.application.dto.UserAccountDto;
import com.hana.securityinboard.application.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.Enumeration;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;
    @GetMapping("/user/join")
    public String joinForm() {
        // 따로 valid 처리안함.
        return "/user/joinForm";
    }
    @PostMapping("/user/join")
    public String join(UserAccountDto userAccountDto) {
        log.info("[UserController - join]");
        userService.saveUser(userAccountDto);
        return "redirect:/home";
    }

    @GetMapping("/user/login")
    public String loginForm() {
        return "/user/loginForm";
    }
    @RequestMapping("/home")
    public String home(Authentication auth) {
        log.info("[UserController home] - auth : {}" , auth);
        return "home";
    }

    @GetMapping("/user/upgradeRole")
    public String upgradeRole(Authentication auth) {
        userService.upgradeRole(auth);
        // TF 여부에 따라서 alert을 다르게 보여주는 자바스크립트 코드가 있으면 더 좋을듯 하다.
        return "home";
    }
}
