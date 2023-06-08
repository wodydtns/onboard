package com.superboard.onbrd.boardgame.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superboard.onbrd.boardgame.repository.FavoriteBoardgameRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteBoardgameServiceImpl implements FavoriteBoardGameService {
	private final FavoriteBoardgameRepository favoriteBoardgameRepository;

	@Override
	@Transactional(readOnly = true)
	public boolean isFavoriteBy(Long boardgameId, String email) {
		return favoriteBoardgameRepository.existsByBoardgame_IdAndMember_Email(boardgameId, email);
	}
}
