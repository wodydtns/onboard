package com.superboard.onbrd.boardgame.service;

import static com.superboard.onbrd.global.exception.ExceptionCode.*;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superboard.onbrd.boardgame.dto.BoardgameDetailDto;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagRequest;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagResponse;
import com.superboard.onbrd.boardgame.dto.RecommandBoardgameDto;
import com.superboard.onbrd.boardgame.entity.Boardgame;
import com.superboard.onbrd.boardgame.entity.NonSearchClickLog;
import com.superboard.onbrd.boardgame.entity.SearchClickLog;
import com.superboard.onbrd.boardgame.repository.BoardNonSearchClickLogRepository;
import com.superboard.onbrd.boardgame.repository.BoardSearchClickLogRepository;
import com.superboard.onbrd.boardgame.repository.BoardgameRepository;
import com.superboard.onbrd.global.exception.BusinessLogicException;

@Service
public class BoardgameServiceImpl implements BoardGameService {
	
	private final String imagePath = "https://objectstorage.ap-seoul-1.oraclecloud.com/n/cnjolvapcoti/b/onboard/o/";

	@Autowired
	private BoardgameRepository boardgameRepository;
	
	@Autowired
	private BoardSearchClickLogRepository boardSearchClickLogRepository;
	
	@Autowired
	private BoardNonSearchClickLogRepository boardNonSearchClickLogRepository;

	@Override
	public Page<BoardgameSearchByTagResponse.BoardGameResponse> searchBoardgameByRecommand(BoardgameSearchByTagRequest boardgameSearchByTagRequest,
		Pageable pageable) {
		Page<BoardgameSearchByTagResponse.BoardGameResponse> boardgameList = boardgameRepository.searchBoardgameByRecommand(boardgameSearchByTagRequest, pageable);
		for (BoardgameSearchByTagResponse.BoardGameResponse boardgame : boardgameList) {
			String imageName = boardgame.getImage();
			boardgame.setImage(imagePath + imageName);
		}
		return boardgameList ;
	}

	@Override
	public BoardgameDetailDto selectBoardgameInfo(Long boardgameId, String referer) {
		BoardgameDetailDto boardgameDetail = boardgameRepository.selectBoardgameInfo(boardgameId);
		String imageName = boardgameDetail.getImage();
		boardgameDetail.setImage(imagePath + imageName);
		
		
		// 추후 refactoring 필요
		if(referer.contains("searchByRecommand")) {
			SearchClickLog isExistClickLog = boardSearchClickLogRepository.findByBoardgameId(boardgameId);
			if(isExistClickLog != null) {
				isExistClickLog.setClickCount(isExistClickLog.getClickCount() + 1);
				boardSearchClickLogRepository.save(isExistClickLog);
			}else {
				SearchClickLog createClickLog = new SearchClickLog();
				createClickLog.setBoardgameId(boardgameId);
				boardSearchClickLogRepository.save(createClickLog);
			}
		}else {
			NonSearchClickLog isExistNonclickLog = boardNonSearchClickLogRepository.findByBoardgameId(boardgameId);
			if(isExistNonclickLog != null) {
				isExistNonclickLog.setClickCount(isExistNonclickLog.getClickCount() + 1);
				boardNonSearchClickLogRepository.save(isExistNonclickLog);
			}else {
				NonSearchClickLog createNonClickLog = new NonSearchClickLog();
				createNonClickLog.setBoardgameId(boardgameId);
				boardNonSearchClickLogRepository.save(createNonClickLog);
			}
		}
		
		return boardgameDetail;
	}

	@Override
	public Page<BoardgameSearchByTagResponse.BoardGameResponse> selectRecommandBoardgameList(Pageable pageable) {
		return boardgameRepository.selectRecommandBoardgameList(pageable);
	}

	@Override
	public Boardgame findVerifiedOneById(Long id) {
		Optional<Boardgame> boardgameOrNull = boardgameRepository.findById(id);

		return boardgameOrNull.orElseThrow(() -> {
			throw new BusinessLogicException(BOARDGAME_NOT_FOUND);
		});
	}

	@Override
	@Transactional(readOnly = true)
	public Long updateFavoriteCount(Long id) {
		return boardgameRepository.updateFavoriteCount(id);
	}

	
}

