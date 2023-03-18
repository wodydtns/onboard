package com.superboard.onbrd.crawling.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.superboard.onbrd.crawling.entity.CrawlingData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.superboard.onbrd.crawling.entity.QCrawlingData.*;

@Repository
@RequiredArgsConstructor
public class CustomCrawlingRepositoryImpl implements CustomCrawlingRepository{
    private final JPAQueryFactory queryFactory;

    public List<String> ifExistUpdateList(List<String> compareExistList){
        return queryFactory.select(crawlingData.boardgameName).from(crawlingData)
                .where(crawlingData.boardgameName.in(compareExistList)).fetch();
    }

    @Override
    public void updateDoneY(List<String> updateList) {
        queryFactory.update(crawlingData).set(crawlingData.doneYn, "Y")
                .where(crawlingData.boardgameName.in(updateList)).execute();
    }


}
