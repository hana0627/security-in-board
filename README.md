# security-in-board - 스프링 시큐리티가지고 장난치는 프로젝트

---

## 개발 환경

* Intellij IDEA Ultimate
* Java 17
* Gradle 7.6.1
* Spring Boot 3.0.6

---

## 기술 세부 스택

Spring Boot

* Spring Web
* Spring Data JPA
* QueryDsl 5.0.0
* Thymeleaf
* Spring Security 6.0.7
* Spring batch
* Oauth2
* Jwt
* MariaDB
* Lombok

---

## 개발기간

2023.05.05 ~ 2023.05.16

---

## 주요기능

* 구글, 네이버, 카카오 인증을 기반으로 한 소셜로그인 및 form Login
* 등급제 게시판 시스템
* 권한별 각기 다른 endPoint 가진다. (5~6개 권한)
* 권한 목록은 아래와 같다.
silver a게시판에 조회만 가능
orange a게시판 b게시판 조회가능. a게시판 글쓰기 가능
red    abc 게시판 조회가능 a게시판 삭제가능. a,b게시판 글쓰기가능
vip    abdc게시판 조회가능. ab게시판 삭제가능 abc게시판 글쓰기 가능
manager   vip권한부여 + 특정 ip나 유저 글쓰기 제한가능 

* springSecurity의 인증을 이용하여 각종 유저정보 저장(마지막 로그인일시저장, 아이피 저장 등)
* 스프링 배치를 이용하여 특정 조건이 완료된 회원은 자동으로 등급업
* aop를 이용한 차단 이용자 접근제한 기능

---

## 프로젝트 회고

