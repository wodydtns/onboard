package com.superboard.onbrd.crawling.entity;

import lombok.Data;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;

@Entity
@Data
@SequenceGenerator(
        name = "CRAWLINGDATA_SEQ_GENERATOR",
        sequenceName = "CRAWLINGDATA_SEQ",
        initialValue = 1, allocationSize = 1
)
@BatchSize(size = 100)
public class CrawlingData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CRAWLINGDATA_SEQ_GENERATOR")
    private Long id;

    @Column(nullable = false)
    private String boardgameName;

    @Column(nullable = false)
    private double pageNum;

    @Column(nullable = false)
    private double gameNumber;

    @Column(nullable = false)
    private String doneYn;

}
