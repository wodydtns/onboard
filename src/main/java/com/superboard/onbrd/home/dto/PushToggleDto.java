package com.superboard.onbrd.home.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PushToggleDto {

    private Long id;

    private Long memberId;

    private String commentYn;

    private String favoriteTagYn;

    @QueryProjection
    public PushToggleDto(Long id, String commentYn, String favoriteTagYn){
        this.id = id;
        this.commentYn=commentYn;
        this.favoriteTagYn=favoriteTagYn;
    }


}
