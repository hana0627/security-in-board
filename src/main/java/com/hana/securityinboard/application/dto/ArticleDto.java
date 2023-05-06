package com.hana.securityinboard.application.dto;

import com.hana.securityinboard.application.domain.Article;
import com.hana.securityinboard.application.domain.UserAccount;
import com.hana.securityinboard.application.domain.constant.RoleType;

import java.time.LocalDateTime;

public record ArticleDto(
        UserAccount userAccount,
        String title,
        String content,
        LocalDateTime createDate,
        String board
) {


    public static ArticleDto of(UserAccount userAccount, String title, String content, LocalDateTime createDate, String board) {
        return new ArticleDto(userAccount, title, content, createDate, board);
    }

    public static ArticleDto form(Article article) {
        return new ArticleDto(
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

    public ArticleDto(UserAccount userAccount, String title, String content, LocalDateTime createDate, String board) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.board = board;
    }

}
