package com.superboard.onbrd.crawling.repository;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.superboard.onbrd.crawling.entity.CrawlingTranslationDto;
import com.superboard.onbrd.global.entity.FCMMessage;
import com.superboard.onbrd.global.util.FCMUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

import static com.superboard.onbrd.auth.entity.QToken.token;
import static com.superboard.onbrd.crawling.entity.QCrawlingData.*;
import static com.superboard.onbrd.boardgame.entity.QBoardGame.*;
import static com.superboard.onbrd.tag.entity.QFavoriteTag.*;
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
                        ,boardGame.id.as("id"),boardGame.description.as("description") ))
                .from(boardGame).fetch();
    }

    @Override
    @Transactional
    @Modifying
    public void updateAllCrawlingTranslationData(List<CrawlingTranslationDto> crawlingTranslationDtoList) {
        for (CrawlingTranslationDto crawlingTranslationDto: crawlingTranslationDtoList){
            queryFactory.update(boardGame)
                    .set(boardGame.description, crawlingTranslationDto.getDescription())
                    .where(boardGame.id.eq(crawlingTranslationDto.getId())).execute();

        }

    }

    /*
    * TODO 
    *  1.favorite tag 리스트 필요 
    *  2.
    * 
    */
    @Override
    public void selectOauthIdForPushMessageByFavorite(HashSet<Long> categoriesTagList) {

        try {
            for (Long categoryTag:categoriesTagList) {
                List<Long> memberIdList = queryFactory.select(member.id).from(favoriteTag)
                        .join(favoriteTag.member, member).where(favoriteTag.id.eq(categoryTag)).groupBy(member.id).fetch();
                for (Long memberId : memberIdList) {
                    String androidPushToken = queryFactory.select(token.androidPushToken).from(token).where(token.id.eq(memberId)).fetchOne();
                    String message = "에 새로운 게임이 추가되었어요!";
                    FCMMessage fcmMessage = FCMMessage.builder().registrationToken(androidPushToken).title("title").message("[#"+ categoryTag +  "]" + message).eventType("NEW_BOARDGAME").boardgameId("14").build();
                    fcmUtil.sendAndroidMessage(fcmMessage);
                }
            }
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
