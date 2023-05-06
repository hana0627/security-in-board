package com.hana.securityinboard.application.controller;

import com.hana.securityinboard.application.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/article")
@Controller
public class ArticleController {

    private final ArticleService articleService;


    @GetMapping("/a")
    public String ArticleA() {
        articleService.searchArticles();
        return "/article/aList";
    }
    @GetMapping("/b")
    public String ArticleB() {
        return "/article/bList";
    }
    @GetMapping("/c")
    public String ArticleC() {
        return "/article/cList";
    }
    @GetMapping("/d")
    public String ArticleD() {
        return "/article/dList";
    }
}
