package com.superboard.onbrd.review.controller;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.global.entity.PageBasicEntity;
import com.superboard.onbrd.review.dto.review.ReviewHomeByFavoriteCount;
import com.superboard.onbrd.review.service.ReviewService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@Api(tags = "추천 리뷰 목록 controller")
public class ReviewHomeController {

    private final ReviewService reviewService;

    @ApiOperation(value = "추천 리뷰 목록")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "offset", value = "페이지 시작", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "페이지 끝", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/curation")
    public List<ReviewHomeByFavoriteCount> selectRecommandReviewList(PageBasicEntity pageBasicEntity) {
        return reviewService.selectRecommandReviewList(pageBasicEntity);
    }
}
