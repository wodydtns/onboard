package com.superboard.onbrd.boardgame.service;

import java.util.List;

import com.superboard.onbrd.boardgame.dto.BoardgameDetailDto;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagRequest;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagResponse;
import com.superboard.onbrd.boardgame.dto.TopBoardgameDto;
import com.superboard.onbrd.boardgame.entity.Boardgame;

public interface BoardGameService {
	public List<BoardgameSearchByTagResponse.BoardGameResponse> searchBoardgameList(BoardgameSearchByTagRequest boardgameSearchByTagRequest) ;
	
	public BoardgameDetailDto selectBoardgameInfo(Long boardgameId, String referer); 
  
	public List<BoardgameSearchByTagResponse.BoardGameResponse> selectRecommandBoardgameList(BoardgameSearchByTagRequest boardgameSearchByTagRequest);
	
	public List<TopBoardgameDto> selectTop10BoardgameList();

	Boardgame findVerifiedOneById(Long id);
	
	public Long updateFavoriteCount(Long id);
}
