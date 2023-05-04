package com.hana.securityinboard.application.dto;

import com.hana.securityinboard.application.domain.Article;
import com.hana.securityinboard.application.domain.ArticleComment;
import com.hana.securityinboard.application.domain.UserAccount;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

public record ArticleCommentDto(
        Article article,
        UserAccount userAccount,
        Long parentCommentId,
        LocalDateTime createDate,
        String content
) {


    public static ArticleCommentDto of(Article article, UserAccount userAccount, Long parentCommentId, LocalDateTime createDate, String content) {
        return new ArticleCommentDto(article, userAccount, parentCommentId, createDate, content);
    }

    public static ArticleCommentDto form(ArticleComment articleComment) {
        return new ArticleCommentDto(
                articleComment.getArticle(),
                articleComment.getUserAccount(),
                articleComment.getParentCommentId(),
                articleComment.getCreateDate(),
                articleComment.getContent()
        );
    }

    public ArticleComment toEntity() {
        return ArticleComment.of(article, userAccount, parentCommentId, createDate, content);
    }

    public ArticleCommentDto(Article article, UserAccount userAccount, Long parentCommentId, LocalDateTime createDate, String content) {
        this.article = article;
        this.userAccount = userAccount;
        this.parentCommentId = parentCommentId;
        this.createDate = createDate;
        this.content = content;
    }
}
