package com.superboard.onbrd.boardgame.service;

import static com.superboard.onbrd.global.exception.ExceptionCode.*;

import java.util.List;
import java.util.Optional;

import com.superboard.onbrd.boardgame.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superboard.onbrd.boardgame.entity.BoardGame;
import com.superboard.onbrd.boardgame.entity.NonSearchClickLog;
import com.superboard.onbrd.boardgame.entity.SearchClickLog;
import com.superboard.onbrd.boardgame.repository.BoardNonSearchClickLogRepository;
import com.superboard.onbrd.boardgame.repository.BoardSearchClickLogRepository;
import com.superboard.onbrd.boardgame.repository.BoardgameRepository;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.global.exception.BusinessLogicException;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardgameServiceImpl implements BoardGameService {

	private final BoardgameRepository boardgameRepository;

	private final BoardSearchClickLogRepository boardSearchClickLogRepository;

	private final BoardNonSearchClickLogRepository boardNonSearchClickLogRepository;

	@Override
	@Transactional(readOnly = true)
	public OnbrdSliceResponse<BoardGameSearchDetail> searchBoardgameList(
		BoardgameSearchByTagRequest boardgameSearchByTagRequest) {

		return boardgameRepository.searchBoardgameList(boardgameSearchByTagRequest);
	}

	@Override
	public BoardGameDetailDto selectBoardgameInfo(Long boardgameId, String referer) {
		BoardGameDetailDto boardgameDetail = boardgameRepository.selectBoardgameInfo(boardgameId);

		// 추후 refactoring 필요
		if (referer.contains("searchByRecommand")) {
			SearchClickLog isExistClickLog = boardSearchClickLogRepository.findByBoardgameId(boardgameId);
			if (isExistClickLog != null) {
				isExistClickLog.setClickCount(isExistClickLog.getClickCount() + 1);
				boardSearchClickLogRepository.save(isExistClickLog);
			} else {
				SearchClickLog createClickLog = new SearchClickLog();
				createClickLog.setBoardgame(isExistClickLog.getBoardgame());
				boardSearchClickLogRepository.save(createClickLog);
			}
		} else {
			NonSearchClickLog isExistNonclickLog = boardNonSearchClickLogRepository.findByBoardgameId(boardgameId);
			if (isExistNonclickLog != null) {
				isExistNonclickLog.setClickCount(isExistNonclickLog.getClickCount() + 1);
				boardNonSearchClickLogRepository.save(isExistNonclickLog);
			} else {
				NonSearchClickLog createNonClickLog = new NonSearchClickLog();
				createNonClickLog.setBoardgame(isExistNonclickLog.getBoardgame());
				boardNonSearchClickLogRepository.save(createNonClickLog);
			}
		}

		return boardgameDetail;
	}

	@Override
	public OnbrdSliceResponse<BoardGameSearchDetail> selectRecommandBoardgameList(
		BoardgameSearchByTagRequest boardgameSearchByTagRequest) {

		return boardgameRepository.selectRecommandBoardgameList(boardgameSearchByTagRequest);
	}

	@Override
	public BoardGame findVerifiedOneById(Long id) {
		Optional<BoardGame> boardgameOrNull = boardgameRepository.findById(id);

		return boardgameOrNull.orElseThrow(() -> {
			throw new BusinessLogicException(BOARDGAME_NOT_FOUND);
		});
	}

	@Override
	public Long updateFavoriteCount(Long id) {
		return boardgameRepository.updateFavoriteCountPlus(id);
	}

	@Override
	public List<TopBoardgameDto> selectTop10BoardgameList() {
		return boardgameRepository.selectTop10BoardgameList();
	}

	@Override
	public Long createFavoriteBoardgameLikeOfDeleteIfExist(FavoriteBoardGameUpdateCommand command) {
		return boardgameRepository.updateFavoriteBoardgameLikes(command);
	}
}

