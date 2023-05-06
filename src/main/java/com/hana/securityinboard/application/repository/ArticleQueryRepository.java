//package com.hana.securityinboard.application.repository;
//
//import com.hana.securityinboard.application.domain.Article;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.stereotype.Repository;
//import static com.hana.securityinboard.application.domain.QArticle.*;
//
//import java.util.List;
//@Repository
//@RequiredArgsConstructor
//public class ArticleQueryRepository {
//    private final JPAQueryFactory queryFactory;
//
//    public Page<Article> findAllWithCondition(PageRequest request) {
////        List<Article> result = queryFactory.select(
////                       )
////                ).from(QArticle.)
//        Article.
//                .offset(request.getOffset())
//                .limit(request.getPageSize())
//                .fetch();
//
////        long total = queryFactory
////                .select(product.count())
////                .from(product)
////                .where(nameCondition(condition.getNameSearchCondition()),
////                        startDateCondition(condition.getStartDate()),
////                        endDateCondition(condition.getEndDate()),
////                        product.isVisible.eq(true),
////                        product.isDelete.eq(false))
////                .join(productFile)
////                .on(productFile.productCode.eq(product.productCode))
////                .fetch().get(0);
//
//        return new PageImpl<>(result, request, total);
//    }
//
//}
