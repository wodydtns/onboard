package com.superboard.onbrd.boardgame.service;

import static com.superboard.onbrd.global.exception.ExceptionCode.*;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.superboard.onbrd.boardgame.dto.SearchBoardGameByRecommand;
import com.superboard.onbrd.boardgame.entity.Boardgame;
import com.superboard.onbrd.boardgame.repository.BoardgameRepository;
import com.superboard.onbrd.global.exception.BusinessLogicException;

@Service
public class BoardgameServiceImpl implements BoardGameService {

	@Autowired
	private BoardgameRepository boardgameRepository;

	@Override
	public Page<Boardgame> searchBoardgameByRecommand(SearchBoardGameByRecommand searchBoardGameByRecommand,
		Pageable pageable) {
		return boardgameRepository.searchBoardgameByRecommand(searchBoardGameByRecommand, pageable);
	}

	@Override
	public Boardgame selectBoardgameInfo(Long boardgameId) {
		return boardgameRepository.selectBoardgameInfo(boardgameId);
	}

	@Override
	public Page<Boardgame> selectBoardgameList(Pageable pageable) {
		return boardgameRepository.selectBoardgameList(pageable);
	}

	@Override
	public Boardgame findVerifiedOneById(Long id) {
		Optional<Boardgame> boardgameOrNull = boardgameRepository.findById(id);

		return boardgameOrNull.orElseThrow(() -> {
			throw new BusinessLogicException(BOARDGAME_NOT_FOUND);
		});
	}
}
