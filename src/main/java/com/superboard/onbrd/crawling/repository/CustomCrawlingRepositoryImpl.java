package com.superboard.onbrd.crawling.repository;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.superboard.onbrd.crawling.entity.CrawlingTranslationDto;
import com.superboard.onbrd.global.util.FCMUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.superboard.onbrd.auth.entity.QToken.token;
import static com.superboard.onbrd.crawling.entity.QCrawlingData.*;
import static com.superboard.onbrd.boardgame.entity.QBoardgame.*;
import static com.superboard.onbrd.boardgame.entity.QFavoriteBoardgame.*;
import static com.superboard.onbrd.member.entity.QMember.*;

@Repository
@RequiredArgsConstructor
public class CustomCrawlingRepositoryImpl implements CustomCrawlingRepository{
    private final JPAQueryFactory queryFactory;

    private final FCMUtil fcmUtil;

    public List<String> ifExistUpdateList(List<String> compareExistList){
        return queryFactory.select(crawlingData.boardgameName).from(crawlingData)
                .where(crawlingData.boardgameName.in(compareExistList)).fetch();
    }

    @Override
    @Modifying
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
    @Modifying
    public void updateAllCrawlingTranslationData(List<CrawlingTranslationDto> crawlingTranslationDtoList) {
        for (CrawlingTranslationDto crawlingTranslationDto: crawlingTranslationDtoList){
            queryFactory.update(boardgame)
                    .set(boardgame.description, crawlingTranslationDto.getDescription())
                    .where(boardgame.id.eq(crawlingTranslationDto.getId())).execute();

        }

    }

    /*
    * TODO 
    *  1. tag id로 favorite 추가한 사람에게 푸시 메시지 보내기
    *  2.
    * */
    @Override
    public void selectOauthIdForPushMessageByFavorite(List<String> categoriesTagList) {
        Long memberId = 1L;
        String refreshToken = queryFactory.select(token.refreshToken).from(token).where(token.id.eq(memberId)).fetchOne();

        try {
            fcmUtil.sendAndroidMessage(refreshToken,"FCM title","fcm Body");
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }


}
