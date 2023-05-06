package com.hana.securityinboard.application.repository;

import com.hana.securityinboard.application.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByBoard(String board);
}
