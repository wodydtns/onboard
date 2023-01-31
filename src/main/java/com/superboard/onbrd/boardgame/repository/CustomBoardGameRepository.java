package com.superboard.onbrd.boardgame.repository;

import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagRequest;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagResponse;

public interface CustomBoardGameRepository {
	BoardgameSearchByTagResponse searchBoardgamesByTag(BoardgameSearchByTagRequest params);
}
