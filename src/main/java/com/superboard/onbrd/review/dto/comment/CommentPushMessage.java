package com.superboard.onbrd.review.dto.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentPushMessage {

    private Long writerId;

    private Long boardGameId;

}
