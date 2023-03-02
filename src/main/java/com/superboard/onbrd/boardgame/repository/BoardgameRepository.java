package com.superboard.onbrd.boardgame.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.superboard.onbrd.boardgame.dto.BoardgameDetailDto;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagRequest;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagResponse;
import com.superboard.onbrd.boardgame.dto.RecommandBoardgameDto;
import com.superboard.onbrd.boardgame.entity.Boardgame;

public interface BoardgameRepository  {
	
	public List<BoardgameSearchByTagResponse.BoardGameResponse> searchBoardgameByRecommand(BoardgameSearchByTagRequest boardgameSearchByTagRequest );
	
	public BoardgameDetailDto selectBoardgameInfo(Long boardgameId); 

	public List<BoardgameSearchByTagResponse.BoardGameResponse> selectRecommandBoardgameList(BoardgameSearchByTagRequest boardgameSearchByTagRequest);

	Optional<Boardgame> findById(Long id);
	
	public Long updateFavoriteCount(Long id);
	
	public void updateClickCount(Long id);
	
}
