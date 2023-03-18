package com.superboard.onbrd.crawling.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@SequenceGenerator(
        name = "CRAWLINGDATA_SEQ_GENERATOR",
        sequenceName = "CRAWLINGDATA_SEQ",
        initialValue = 1, allocationSize = 1
)
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
