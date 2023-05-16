package com.hana.securityinboard.application.controller;

import com.hana.securityinboard.application.dto.UserAccountDto;
import com.hana.securityinboard.application.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;
    @RequestMapping("/home")
    public String home(Authentication auth) {
        log.info("[UserController home] - auth : {}" , auth);
        return "home";
    }


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


    @GetMapping("/user/upgradeRole")
    public String upgradeRole(Authentication auth) {
        userService.upgradeRole(auth);
        // TF 여부에 따라서 alert을 다르게 보여주는 자바스크립트 코드가 있으면 더 좋을듯 하다.
        return "home";
    }


    @GetMapping("/user/manager")
    public String management(Authentication auth) {
        if(userService.isAdmin(auth)) {
            return "/user/managementAdmin";
        }
        if(userService.isManager(auth)){
            return "/user/managementManager";
        }
        return "home";
    }

    @GetMapping("/user/searchAllUser")
    public String searchAllUser(Model model) {
        List<UserAccountDto> users = userService.searchAllUser();
        model.addAttribute("users",users);
        return "/user/userList";
    }

    @GetMapping("/user/detail/{username}")
    public String userDetail(@PathVariable String username, Model model) {
        UserAccountDto user = userService.findUser(username);
        model.addAttribute("user",user);

        return "/user/userDetail";
    }

    @PostMapping("/user/registManager/{username}")
    public String addManager(@PathVariable String username) {
        userService.createManger(username);
        return "redirect:/user/userList/";
    }

    @PostMapping("/user/block/{username}")
    public String blockUser(@PathVariable String username) {
        log.info("[UserController - blockUser] - called");
        log.info("username = {}" , username);
        userService.blockUser(username);
        return "home";
    }

}
