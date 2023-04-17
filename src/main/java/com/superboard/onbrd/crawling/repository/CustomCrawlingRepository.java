package com.superboard.onbrd.crawling.repository;

import com.superboard.onbrd.crawling.entity.CrawlingTranslationDto;

import java.util.HashSet;
import java.util.List;

public interface CustomCrawlingRepository {

    public List<String> ifExistUpdateList(List<String> compareExistList);

    public void updateDoneY(List<String> updateList);

    public List<CrawlingTranslationDto> selectAllBoardGameDescription();

    public void updateAllCrawlingTranslationData(List<CrawlingTranslationDto > crawlingTranslationDtoList);

    public void selectOauthIdForPushMessageByFavorite(HashSet<Long> categoriesTagList);

}
