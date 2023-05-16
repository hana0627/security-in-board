package com.hana.securityinboard.application.domain;

import com.hana.securityinboard.application.domain.constant.RoleType;
import com.hana.securityinboard.application.domain.constant.RoleTypesConverter;
import com.hana.securityinboard.application.dto.UserAccountDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
    private Integer articleCount; // 작성한 게시글 수

    private Boolean isBlocked; // 차단당했는지 여부
    private LocalDateTime blockedDate; //언제 차단 당했는지


    public static UserAccount of(String username, String password, String email, String name, RoleType role) {
        return of(username, password, email, name, LocalDateTime.now(), LocalDateTime.now(), null, role, 1, 0);
    }

    public static UserAccount of(String username, String password, String email, String name, LocalDateTime accountDate, LocalDateTime lastLoginDate, String lastLoginIp, RoleType roleType, Integer loginDay, Integer articleCount) {
        return new UserAccount(username, password, email, name, accountDate, lastLoginDate, lastLoginIp, roleType, loginDay, articleCount, false);
    }


    private UserAccount(String username, String password, String email, String name, LocalDateTime accountDate, LocalDateTime lastLoginDate, String lastLoginIp, RoleType roleType, Integer loginDay, Integer articleCount, Boolean isBlocked) {
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
        this.isBlocked = isBlocked;
    }


    //로그인 횟수 계산
    public void loginDayCounter() {
        // 로그인한 일자로 비교
        // 마지막 로그인한 요일과 현재 요일이 다르면
        if(this.lastLoginDate.getDayOfWeek().getValue() != LocalDateTime.now().getDayOfWeek().getValue()) {
            this.lastLoginDate = LocalDateTime.now();
            loginDay++;
        }
        // 혹시나 정확히 일주일만에 로그인 한 경우
        else if(this.lastLoginDate.getMonth().getValue() == LocalDateTime.now().getMonth().getValue()) {
            // 최소한 주가 같으면 안됨
            if(this.lastLoginDate.getDayOfMonth() != LocalDateTime.now().getDayOfMonth()){
                loginDay++;
            };
        }
    }

    public void setLastLoginIp(String remoteAddress) {
        this.lastLoginIp = remoteAddress;
    }

    public void articleCountPlus() {
        this.articleCount++;
    }


    /*
     SILVER // 기본권한
     ORANGE // 자동등업
     RED    // 3일 이상 로그인
     VIP    // 10일 이상 로그인, 글 3개
      */
    public boolean canUpgrade(UserAccount userAccount) {
        return switch (userAccount.getRoleType()) {
            case SILVER -> true;
            case ORANGE -> userAccount.loginDay >= 3;
            case RED -> userAccount.loginDay >= 10 && userAccount.getArticleCount() >= 3;
            default ->  false;
        };
    }


    public void upgradeRole(UserAccount userAccount) {
        this.roleType = RoleType.upgradeRole(userAccount.getRoleType());
    }


    public void changeRoleManager(UserAccount user) {
        this.roleType = RoleType.MANAGER;
    }

    public void heIsBlocked() {
        this.isBlocked = true;
        this.blockedDate = LocalDateTime.now();
    }

    public void heIsLiberated(UserAccount user) {
        this.isBlocked = false;
    }


    /**
     * 차단된 사용자면 false를 return
     * 차단되지 않은 사용자면 true를 return
     */
    public boolean isBlock(UserAccount user) {
        if(user.getIsBlocked().equals(false)) {
            return true;
        }
        if(user.getIsBlocked().equals(true)) {
            // 처음 차단된지 7일 이상이면
            if(ChronoUnit.DAYS.between(user.getBlockedDate(), LocalDateTime.now()) >= 7) {
                user.heIsLiberated(user);
                return true;
            }
        }
        return false;
    }

}
