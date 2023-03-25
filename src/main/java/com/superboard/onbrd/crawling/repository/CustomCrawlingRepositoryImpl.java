package com.superboard.onbrd.crawling.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.superboard.onbrd.boardgame.entity.Boardgame;
import com.superboard.onbrd.crawling.entity.CrawlingData;
import com.superboard.onbrd.crawling.entity.CrawlingTranslationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.superboard.onbrd.crawling.entity.QCrawlingData.*;
import static com.superboard.onbrd.boardgame.entity.QBoardgame.*;

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

    @Override
    public List<CrawlingTranslationDto> selectAllBoardgameDescription() {
        return queryFactory.select(Projections.constructor(CrawlingTranslationDto.class
                        ,boardgame.id.as("id"),boardgame.description.as("description") ))
                .from(boardgame).fetch();
    }

    @Override
    @Transactional
    public void updateAllCrawlingTranslationData(List<CrawlingTranslationDto> crawlingTranslationDtoList) {
        for (CrawlingTranslationDto crawlingTranslationDto: crawlingTranslationDtoList){
            System.out.println(crawlingTranslationDto.getDescription());
            queryFactory.update(boardgame)
                    .set(boardgame.description, crawlingTranslationDto.getDescription())
                    .where(boardgame.id.eq(crawlingTranslationDto.getId())).execute();

        }

    }


}
