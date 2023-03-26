package com.superboard.onbrd.crawling.repository;

import com.superboard.onbrd.boardgame.entity.Boardgame;
import com.superboard.onbrd.crawling.entity.CrawlingData;
import com.superboard.onbrd.crawling.entity.CrawlingTranslationDto;
import com.superboard.onbrd.tag.entity.BoardgameTag;

import java.util.List;

public interface CustomCrawlingRepository {

    public List<String> ifExistUpdateList(List<String> compareExistList);

    public void updateDoneY(List<String> updateList);

    public List<CrawlingTranslationDto> selectAllBoardgameDescription();

    public void updateAllCrawlingTranslationData(List<CrawlingTranslationDto > crawlingTranslationDtoList);

    public void selectOauthIdForPushMessageByFavorite(List<String> categoriesTagList);

}
