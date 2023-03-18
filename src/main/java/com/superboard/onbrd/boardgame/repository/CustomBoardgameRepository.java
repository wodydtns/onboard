package com.superboard.onbrd.boardgame.repository;

import com.superboard.onbrd.boardgame.dto.BoardgameDetailDto;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagRequest;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagResponse;
import com.superboard.onbrd.boardgame.dto.TopBoardgameDto;
import com.superboard.onbrd.boardgame.entity.Boardgame;

import java.util.List;
import java.util.Optional;

public interface CustomBoardgameRepository {
	
	public List<BoardgameSearchByTagResponse.BoardGameResponse> searchBoardgameByRecommand(BoardgameSearchByTagRequest boardgameSearchByTagRequest );
	
	public BoardgameDetailDto selectBoardgameInfo(Long boardgameId); 

	public List<BoardgameSearchByTagResponse.BoardGameResponse> selectRecommandBoardgameList(BoardgameSearchByTagRequest boardgameSearchByTagRequest);
	
	public List<TopBoardgameDto> selectTop10BoardgameList();

	Optional<Boardgame> findById(Long id);
	
	public Long updateFavoriteCount(Long id);
	
	public void updateClickCount(Long id);


}
