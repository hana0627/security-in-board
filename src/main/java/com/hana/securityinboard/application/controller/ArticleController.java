package com.hana.securityinboard.application.controller;

import com.hana.securityinboard.application.dto.ArticleCommentDtoForQuery;
import com.hana.securityinboard.application.dto.ArticleDto;
import com.hana.securityinboard.application.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        ArticleDto check = articles.getContent().get(0);
        System.out.println("check : " + check.toString());
        System.out.println("사용자 이름 " + check.userAccount().getName());
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
        log.info("[Controller showArticle] -called");
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
