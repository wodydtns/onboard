package com.superboard.onbrd.crawling.entity;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CrawlingTranslationDto {

    private Long id;

    private String description;

    @QueryProjection
    public CrawlingTranslationDto(Long id, String description){
        this.id = id;
        this.description = description;
    }
}
