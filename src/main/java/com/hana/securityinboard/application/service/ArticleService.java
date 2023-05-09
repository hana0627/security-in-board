package com.hana.securityinboard.application.service;

import com.hana.securityinboard.application.dto.ArticleCommentDto;
import com.hana.securityinboard.application.dto.ArticleCommentDtoForQuery;
import com.hana.securityinboard.application.dto.ArticleDto;
import com.hana.securityinboard.application.repository.ArticleQueryRepository;
import com.hana.securityinboard.application.repository.ArticleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleQueryRepository articleQueryRepository;
    public Page<ArticleDto> searchArticles(String board, Pageable pageable) {

        PageRequest request = pageRequest(pageable, 50);

        //페이지네이션은 천천히 처리
        return articleQueryRepository.findAllWithCondition(board, request)
                .map(ArticleDto::form);
//        return null;
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
    private static PageRequest pageRequest(Pageable pageable, int pageSize) {
        int current_page = pageable.getPageNumber() < 1 ? 0 : pageable.getPageNumber() - 1;
        return PageRequest.of(current_page, pageSize);
    }
}
