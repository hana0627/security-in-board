package com.hana.securityinboard.application.repository;

import com.hana.securityinboard.application.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
