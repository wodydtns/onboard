package com.superboard.onbrd.home.controller;

import com.superboard.onbrd.crawling.repository.CustomCrawlingRepository;
import com.superboard.onbrd.review.repository.CustomCommentRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import oracle.ucp.proxy.annotation.Post;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

@RestController
@RequestMapping("/api/v1/FCMPush")
@RequiredArgsConstructor
@Api(tags = {"FCM push test Controller"})
public class FCMPushTokenTestController {

    private final CustomCrawlingRepository customCrawlingRepository;

    private final CustomCommentRepository customCommentRepository;
    @ApiOperation(value = "관심있는 태그의 게임 추가 시 FCM message push")
    @PostMapping("/PushMessageByFavorite")
    public void PushMessageByFavorite(HashSet<Long> categoriesTagList){
        customCrawlingRepository.selectOauthIdForPushMessageByFavorite(categoriesTagList);
    }
    @ApiOperation(value = "자신이 작성한 리뷰에 댓글 추가 시 FCM message push")
    @PostMapping("/PushMessageByFavorite")
    public void PushMessageByComment(long createdId){
        customCommentRepository.selectOauthIdForPushMessage(createdId);
    }
}
