package com.superboard.onbrd.crawling.repository;

import com.superboard.onbrd.crawling.entity.CrawlingData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrawlingRepository extends JpaRepository<CrawlingData, Long> {
}
