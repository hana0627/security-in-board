package com.hana.securityinboard.application.service;

import com.hana.securityinboard.application.domain.Article;
import com.hana.securityinboard.application.dto.ArticleDto;
import com.hana.securityinboard.application.repository.ArticleRepository;
import jakarta.persistence.EntityNotFoundException;
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
    public List<ArticleDto> searchArticles(String board) {
        return articleRepository.findAllByBoard(board)
                .stream()
                .map(ArticleDto::form).toList();
    }

    @Transactional
    public void createArticle(ArticleDto dto) {
        articleRepository.save(dto.toEntity());
    }

    public ArticleDto showArticle(Long id) {
        return articleRepository.findById(id)
                .map(ArticleDto::form)
                .orElseThrow(() -> new EntityNotFoundException("엔티티를 찾을 수 없습니다!"));
    }
}
