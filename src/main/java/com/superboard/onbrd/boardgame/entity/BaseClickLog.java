package com.superboard.onbrd.boardgame.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;

public class BaseClickLog {

	@Column(nullable = false)
	private LocalDateTime clickAt = LocalDateTime.now();
	
	@Column(nullable = false)
	private long clickCount = 1;
	
	@Column(nullable = false)
	private Long boardgameId;
}
