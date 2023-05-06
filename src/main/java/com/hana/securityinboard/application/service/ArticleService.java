package com.hana.securityinboard.application.service;

import com.hana.securityinboard.application.domain.Article;
import com.hana.securityinboard.application.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    public List<Article> searchArticles() {
        return null;
    }
}
