package com.hana.securityinboard.application.dto;

import com.hana.securityinboard.application.domain.UserAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * QueryDsl 5.0.0 버전에선 record타입을 지원하지 않아서
 * 순수 쿼리용으로만 만든 dto. 다른곳에서 사용하면 안됨!
 */
@ToString
@NoArgsConstructor
@Setter
@Getter
public class ArticleCommentDtoForQuery {
    private Long id;
    private UserAccount userAccount;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private String board;
}
