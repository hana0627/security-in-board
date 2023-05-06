package com.hana.securityinboard.application.service;

import com.hana.securityinboard.application.domain.Article;
import com.hana.securityinboard.application.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    public List<Article> searchAArticles() {
        return articleRepository.findAll();
    }
    public List<Article> searchBArticles() {
        return articleRepository.findAll();
    }
    public List<Article> searchCArticles() {
        return articleRepository.findAll();
    }
    public List<Article> searchDArticles() {
        return articleRepository.findAll();
    }
}
