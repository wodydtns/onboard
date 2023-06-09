package com.superboard.onbrd.boardgame.service;

public interface FavoriteBoardGameService {
	boolean isFavoriteBy(Long boardgameId, String email);
}
