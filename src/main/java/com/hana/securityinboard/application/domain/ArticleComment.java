package com.hana.securityinboard.application.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class ArticleComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private Article article; // 게시글(ID)

    @ManyToOne(optional = false)
    @JoinColumn(name = "userId")
    private UserAccount userAccount; // 유저 정보 (ID)

    @Column(updatable = false)
    private Long parentCommentId; // 부모 댓글 ID

    @CreatedDate
    private LocalDateTime createDate; // 작성시간

    @Column(nullable = false, length = 500)
    private String content; // 본문


    public static ArticleComment of(Article article, UserAccount userAccount, Long parentCommentId, LocalDateTime createDate, String content) {
        return new ArticleComment(article, userAccount, parentCommentId, createDate, content);
    }

    private ArticleComment(Article article, UserAccount userAccount, Long parentCommentId, LocalDateTime createDate, String content) {
        this.article = article;
        this.userAccount = userAccount;
        this.parentCommentId = parentCommentId;
        this.createDate = createDate;
        this.content = content;
    }
}
