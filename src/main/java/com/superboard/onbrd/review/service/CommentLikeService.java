package com.superboard.onbrd.review.service;

public interface CommentLikeService {
	void createCommentLikeOrDeleteIfExist(String email, Long commentId);
}
