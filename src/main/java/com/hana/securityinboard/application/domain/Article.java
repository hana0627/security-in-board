package com.hana.securityinboard.application.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "userId")
    @ManyToOne(optional = false)
    private UserAccount userAccount; // 유저 정보 (ID)

    @Column(nullable = false, length = 255)
    private String title; // 제목
    @Column(nullable = false, length = 10000)
    private String content; // 본문
    @CreatedDate
    private LocalDateTime createDate; // 작성시간
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final List<ArticleComment> articleComments = new LinkedList<>();

    public static Article of(UserAccount userAccount, String title, String content, LocalDateTime createDate) {
        return new Article(userAccount, title, content, createDate);
    }

    private Article(UserAccount userAccount, String title, String content, LocalDateTime createDate) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
    }
}
