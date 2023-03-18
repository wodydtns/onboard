package com.superboard.onbrd.crawling.repository;

import com.superboard.onbrd.crawling.entity.CrawlingData;

import java.util.List;

public interface CustomCrawlingRepository {

    public List<String> ifExistUpdateList(List<String> crawlingDataList);

    public void updateDoneY(List<String> updateList);
}
