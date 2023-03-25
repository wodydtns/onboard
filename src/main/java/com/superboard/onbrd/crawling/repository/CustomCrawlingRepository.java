package com.superboard.onbrd.crawling.repository;

import com.superboard.onbrd.boardgame.entity.Boardgame;
import com.superboard.onbrd.crawling.entity.CrawlingData;
import com.superboard.onbrd.crawling.entity.CrawlingTranslationDto;

import java.util.List;

public interface CustomCrawlingRepository {

    public List<String> ifExistUpdateList(List<String> crawlingDataList);

    public void updateDoneY(List<String> updateList);

    public List<CrawlingTranslationDto> selectAllBoardgameDescription();

    public void updateAllCrawlingTranslationData(List<CrawlingTranslationDto > crawlingTranslationDtoList);
}
