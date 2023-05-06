package com.hana.securityinboard.application.dto;

import com.hana.securityinboard.application.domain.Article;
import com.hana.securityinboard.application.domain.UserAccount;
import com.hana.securityinboard.application.domain.constant.RoleType;

import java.time.LocalDateTime;

public record ArticleDto(
        Long id,
        UserAccount userAccount,
        String title,
        String content,
        LocalDateTime createDate,
        String board
) {


    public static ArticleDto of(UserAccount userAccount, String title, String content, LocalDateTime createDate, String board) {
        return new ArticleDto(null ,userAccount, title, content, createDate, board);
    }

    public static ArticleDto form(Article article) {
        return new ArticleDto(
                article.getId(),
                article.getUserAccount(),
                article.getTitle(),
                article.getContent(),
                article.getCreateDate(),
                article.getBoard()
        );
    }

    public Article toEntity() {
        return Article.of(userAccount, title, content, createDate, board);
    }

    public ArticleDto(Long id, UserAccount userAccount, String title, String content, LocalDateTime createDate, String board) {
        this.id = id;
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.board = board;
    }

}
