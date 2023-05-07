package com.hana.securityinboard.application.domain;

import com.hana.securityinboard.application.domain.constant.RoleType;
import com.hana.securityinboard.application.domain.constant.RoleTypesConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserAccount {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(nullable = false, length = 255,unique = true)
    private String username; // 로그인아이디
    @Column(length = 255)
    private String password; //비밀번호
    @Column(length = 100)
    private String email; //이메일
    @Column(length = 100)
    private String name;// 이름
    private LocalDateTime accountDate; //계정 생성일자
    private LocalDateTime lastLoginDate; // 마지막 접속일
    @Column(length = 100)
    private String lastLoginIp; // 마지막접속한ip
    @Column(length = 100)
    @Convert(converter = RoleTypesConverter.class)
    private RoleType roleType; // 관리자, 운영자, 유저 한명은 한개의 Role만 가질 수 있음

    private Integer loginDay; // 총 몇번 접속했는지
    private Integer articleCount; // 몇개의 게시글
    private Integer articleCommentCount; //몇개의 댓글

    public static UserAccount of(String username, String password, String email, String name, RoleType role) {
        return of(username, password, email, name, LocalDateTime.now(), LocalDateTime.now(), null, role, 1, 0, 0);
    }

    public static UserAccount of(String username, String password, String email, String name, LocalDateTime accountDate, LocalDateTime lastLoginDate, String lastLoginIp, RoleType roleType, Integer loginDay, Integer articleCount, Integer articleCommentCount) {
        return new UserAccount(username, password, email, name, accountDate, lastLoginDate, lastLoginIp, roleType, loginDay, articleCount, articleCommentCount);
    }


    private UserAccount(String username, String password, String email, String name, LocalDateTime accountDate, LocalDateTime lastLoginDate, String lastLoginIp, RoleType roleType, Integer loginDay, Integer articleCount, Integer articleCommentCount) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.accountDate = accountDate;
        this.lastLoginDate = lastLoginDate;
        this.lastLoginIp = lastLoginIp;
        this.roleType = roleType;
        this.loginDay = loginDay;
        this.articleCount = articleCount;
        this.articleCommentCount = articleCommentCount;
    }


    //로그인 횟수 계산
    public void loginDayCounter() {
        // 로그인한 일자로 비교
        if(this.lastLoginDate.getDayOfWeek().getValue() != LocalDateTime.now().getDayOfWeek().getValue()) {
            this.lastLoginDate = LocalDateTime.now();
            loginDay++;
        }
        // 혹시나 정확히 일주일만에 로그인 한 경우
        else if(this.lastLoginDate.getMonth().getValue() == LocalDateTime.now().getMonth().getValue()) {
            this.lastLoginDate = LocalDateTime.now();
            loginDay++;
        }
        // 1년에 한번 로그인 하는 경우는 없다고 가정
    }

    public void setLastLoginIp(String remoteAddress) {
        this.lastLoginIp = remoteAddress;
    }
}
