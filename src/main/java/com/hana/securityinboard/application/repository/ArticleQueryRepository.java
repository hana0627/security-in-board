//package com.hana.securityinboard.application.repository;
//
////import com.hana.securityinboard.application.domain.Article;
////import com.hana.securityinboard.application.domain.QArticle;
////import com.hana.securityinboard.application.dto.ArticleDto;
////import com.querydsl.core.types.Projections;
////import com.querydsl.jpa.impl.JPAQueryFactory;
////import lombok.RequiredArgsConstructor;
////import org.springframework.data.domain.Page;
////import org.springframework.data.domain.PageImpl;
////import org.springframework.data.domain.PageRequest;
////import org.springframework.stereotype.Repository;
//
//
//import static com.hana.securityinboard.application.domain.QArticle.article;
//
//import com.hana.securityinboard.application.dto.ArticleDto;
//import com.querydsl.core.types.Projections;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import java.util.List;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//@RequiredArgsConstructor
//public class ArticleQueryRepository {
//    private final JPAQueryFactory queryFactory;
//
//    //TODO : N+1 쿼리 터질지도 모름. 잘 보고 관리
//    public Page<ArticleDto> findAllWithCondition(String board, PageRequest pageable) {
//
//        List<ArticleDto> result = queryFactory.select(
//                        Projections.fields(ArticleDto.class,
//                                article.id,
//                                article.title,
//                                article.createDate,
//                                article.userAccount.name
//                        )).from(article)
//                .where(article.board.eq(board))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        long total = queryFactory.select(article.count())
//                .from(article)
//                .where(article.board.eq(board))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch().get(0);
//
//        return new PageImpl<>(result, pageable, total);
//    }
//
//}
