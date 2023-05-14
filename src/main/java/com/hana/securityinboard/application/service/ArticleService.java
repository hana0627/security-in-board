package com.hana.securityinboard.application.service;

import com.hana.securityinboard.application.domain.Article;
import com.hana.securityinboard.application.domain.UserAccount;
import com.hana.securityinboard.application.domain.constant.RoleType;
import com.hana.securityinboard.application.dto.ArticleCommentDto;
import com.hana.securityinboard.application.dto.ArticleCommentDtoForQuery;
import com.hana.securityinboard.application.dto.ArticleDto;
import com.hana.securityinboard.application.repository.ArticleQueryRepository;
import com.hana.securityinboard.application.repository.ArticleRepository;
import com.hana.securityinboard.application.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleQueryRepository articleQueryRepository;
    private final UserRepository userRepository;

    public Page<ArticleDto> searchArticles(String board, Pageable pageable) {

        PageRequest request = pageRequest(pageable, 50);

        //페이지네이션은 천천히 처리
        return articleQueryRepository.findAllWithCondition(board, request)
                .map(ArticleDto::form);
//        return null;
    }

    @Transactional
    public ArticleDto createArticle(ArticleDto dto, Authentication auth) {

        UserAccount userAccount = userRepository.findByUsername(auth.getName()).orElseThrow(EntityNotFoundException::new);
        ArticleDto.of(userAccount,dto);

        // 작성자의 게시글 카운트 증가.
        // 위의 메소드 안에 넣는 방법도 고민해보았지만, 분리하는것이 더 나아보인다.
        userAccount.articleCountPlus();
        
        return ArticleDto.form(articleRepository.save(ArticleDto.of(userAccount, dto).toEntity()));
    }

    public ArticleDto showArticle(Long id) {

        return articleRepository.findById(id)
                .map(ArticleDto::form)
                .orElseThrow(() -> new EntityNotFoundException("엔티티를 찾을 수 없습니다!"));
    }

    // 해당 게시판에 글쓰기가 가능한지 확인하는 코드
    public boolean canCreateArticle(String username, String board) {
        UserAccount userAccount = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException());
        return RoleType.canCreateArticle(userAccount.getRoleType().getRoleName(), board);
    }

    @Transactional
    public String deleteArticle(Long articleId, Authentication auth) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new EntityNotFoundException());
        UserAccount authUser = userRepository.findByUsername(auth.getName()).orElseThrow(() -> new EntityNotFoundException());;
        UserAccount writeUser = article.getUserAccount();

        boolean isHimSelf = authUser.equals(writeUser);
        boolean canDelete = RoleType.canDeleteArticle(authUser.getRoleType().getRoleName(), article.getBoard());

        if (isHimSelf || canDelete) {
            articleRepository.deleteById(articleId);
        }
        return article.getBoard();
    }

    private static PageRequest pageRequest(Pageable pageable, int pageSize) {
        int current_page = pageable.getPageNumber() < 1 ? 0 : pageable.getPageNumber() - 1;
        return PageRequest.of(current_page, pageSize);
    }

}
