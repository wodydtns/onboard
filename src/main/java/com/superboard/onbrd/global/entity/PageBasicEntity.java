package com.superboard.onbrd.global.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "pagination 파라미터")
public class PageBasicEntity {

    @ApiModelProperty(example = "1", notes = "page 시작")
    private long offset;
    @ApiModelProperty(example = "10", notes = "페이지 끝")
    private int limit;
}
