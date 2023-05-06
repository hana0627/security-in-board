package com.hana.securityinboard.application.controller;

import com.hana.securityinboard.application.dto.ArticleDto;
import com.hana.securityinboard.application.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/article")
@Controller
public class ArticleController {

    private final ArticleService articleService;


    @GetMapping("/a")
    public String ArticleA(Model model) {
        List<ArticleDto> articles = articleService.searchArticles("A");
        model.addAttribute("articles", articles);
        return "article/articleList";
    }

    @GetMapping("/b")
    public String ArticleB(Model model) {
        List<ArticleDto> articles = articleService.searchArticles("B");
        model.addAttribute("articles", articles);
        return "article/articleList";
    }

    @GetMapping("/c")
    public String ArticleC(Model model) {
        List<ArticleDto> articles = articleService.searchArticles("C");
        model.addAttribute("articles", articles);
        return "article/articleList";
    }

    @GetMapping("/d")
    public String ArticleD(Model model) {
        List<ArticleDto> articles = articleService.searchArticles("D");
        model.addAttribute("articles", articles);
        return "article/articleList";
    }

    @GetMapping("/show/{id}")
    public String showArticle(@PathVariable Long id, Model model) {
        ArticleDto article = articleService.showArticle(id);
        model.addAttribute("article",article);
        return "/article/article";
    }

    @PostMapping("/create")
    public String createArticle(ArticleDto articleDto) {
        articleService.createArticle(articleDto);
        //TODO 좀만 더 생각. 해당 게시판으로 보내야함
        return "redirect:/home";
    }
}
