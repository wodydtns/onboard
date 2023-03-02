package com.superboard.onbrd.boardgame.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.superboard.onbrd.boardgame.dto.BoardgameDetailDto;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagRequest;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagResponse;
import com.superboard.onbrd.boardgame.dto.RecommandBoardgameDto;
import com.superboard.onbrd.boardgame.entity.Boardgame;

public interface BoardGameService {
	public List<BoardgameSearchByTagResponse.BoardGameResponse> searchBoardgameByRecommand(BoardgameSearchByTagRequest boardgameSearchByTagRequest) ;
	
	public BoardgameDetailDto selectBoardgameInfo(Long boardgameId, String referer); 
  
	public List<BoardgameSearchByTagResponse.BoardGameResponse> selectRecommandBoardgameList(BoardgameSearchByTagRequest boardgameSearchByTagRequest);

	Boardgame findVerifiedOneById(Long id);
	
	public Long updateFavoriteCount(Long id);
}
