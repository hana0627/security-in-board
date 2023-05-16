package com.hana.securityinboard.application.controller;

import com.hana.securityinboard.application.domain.UserAccount;
import com.hana.securityinboard.application.dto.ArticleDto;
import com.hana.securityinboard.application.service.ArticleService;
import com.hana.securityinboard.global.aop.annotation.UserIsBlocked;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @UserIsBlocked
    @GetMapping("/b")
    public String ArticleB(Model model, Pageable pageable) {
        Page<ArticleDto> articles = articleService.searchArticlesWithBlockCheck("B", pageable);
        model.addAttribute("articles", articles);
            model.addAttribute("article", articles.getContent().get(0));

        System.out.println("size : " + articles.getContent().size());
        return "article/articleList";
    }
    @UserIsBlocked
    @GetMapping("/c")
    public String ArticleC(Model model, Pageable pageable) {
        Page<ArticleDto> articles = articleService.searchArticlesWithBlockCheck("C", pageable);
        model.addAttribute("articles", articles);
            model.addAttribute("article", articles.getContent().get(0));

        System.out.println("size : " + articles.getContent().size());
        return "article/articleList";
    }
    @UserIsBlocked
    @GetMapping("/d")
    public String ArticleD(Model model, Pageable pageable) {
        Page<ArticleDto> articles = articleService.searchArticlesWithBlockCheck("D", pageable);

        model.addAttribute("articles", articles);

        System.out.println("size : " + articles.getContent().size());
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
        if(articleService.canCreateArticle(username, board)) {
            model.addAttribute("username", username);
            model.addAttribute("board", board);
            return "/article/createForm";
        }
        return "home";
    }

    @PostMapping("/create")
    public String createArticle(ArticleDto articleDto, Authentication auth) {
        ArticleDto article = articleService.createArticle(articleDto, auth);
        return "redirect:/article/" + article.board().toLowerCase();
    }

    @PostMapping("/delete")
    public String deleteArticle(@RequestParam Long articleId, Authentication auth) {
        String board = articleService.deleteArticle(articleId, auth);
        return "redirect:/article/" + board.toLowerCase();
    }
}
