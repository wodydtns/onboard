package com.superboard.onbrd.home.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PushToggleDto {

    private Long id;

    private String commentYn;

    private String favoriteTagYn;

    @QueryProjection
    public PushToggleDto(Long id, String commentYn, String favoriteTagYn){
        this.id = id;
        this.commentYn=commentYn;
        this.favoriteTagYn=favoriteTagYn;
    }

    public static PushToggleDto of(PushTogglePatchRequest request){
        PushToggleDto dto = new PushToggleDto();
        dto.id = request.getId();
        dto.commentYn=request.getCommentYn();
        dto.favoriteTagYn=request.getFavoriteTagYn();
        return dto;
    }
}
