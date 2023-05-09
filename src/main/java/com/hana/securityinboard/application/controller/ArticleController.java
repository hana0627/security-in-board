package com.hana.securityinboard.application.controller;

import com.hana.securityinboard.application.dto.ArticleDto;
import com.hana.securityinboard.application.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/article")
@Controller
public class ArticleController {

    private final ArticleService articleService;


    @GetMapping("/a")
    public String ArticleA(Model model, Pageable pageable) {
        Page<ArticleDto> articles = articleService.searchArticles("A", pageable);
        model.addAttribute("articles", articles);
        model.addAttribute("article", articles.getContent().get(0));
        return "article/articleList";
    }

    @GetMapping("/b")
    public String ArticleB(Model model, Pageable pageable) {
        Page<ArticleDto> articles = articleService.searchArticles("B", pageable);
        model.addAttribute("articles", articles);
        return "article/articleList";
    }

    @GetMapping("/c")
    public String ArticleC(Model model, Pageable pageable) {
        Page<ArticleDto> articles = articleService.searchArticles("C", pageable);
        model.addAttribute("articles", articles);
        return "article/articleList";
    }

    @GetMapping("/d")
    public String ArticleD(Model model, Pageable pageable) {
        Page<ArticleDto> articles = articleService.searchArticles("D", pageable);
        model.addAttribute("articles", articles);
        return "article/articleList";
    }

    @GetMapping("/show/{id}")
    public String showArticle(@PathVariable Long id, Model model) {
        ArticleDto article = articleService.showArticle(id);
        model.addAttribute("article",article);
        return "/article/article";
    }

    @GetMapping("/create")
    public String createArticleForm(@RequestParam String username, @RequestParam String board, Model model) {
        model.addAttribute("username", username);
        model.addAttribute("board", board);
        return "/article/createForm";
    }

    @PostMapping("/create")
    public String createArticle(ArticleDto articleDto, Authentication auth) {
        ArticleDto article = articleService.createArticle(articleDto, auth);
        return "redirect:/article/" + article.board().toLowerCase();
    }
}
