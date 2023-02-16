package com.superboard.onbrd.boardgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.boardgame.dto.BoardgameDetailDto;
import com.superboard.onbrd.boardgame.dto.SearchBoardGameByRecommand;
import com.superboard.onbrd.boardgame.entity.Boardgame;
import com.superboard.onbrd.boardgame.service.BoardGameService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/boardgames")
@RequiredArgsConstructor
@Validated
@Api(tags = {"BoardGame 정보를 제공하는 Controller"})
public class BoardgameController {

	@Autowired
	private final BoardGameService boardGameService;

	@ApiOperation(value = "사용자의 이름과 나이를 리턴하는 메소드")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "playCount", value = "인원", required = true, dataType = "varchar", paramType = "path"),
		@ApiImplicitParam(name = "difficulty", value = "난이도", required = true, dataType = "varchar", paramType = "path"),
		@ApiImplicitParam(name = "name", value = "장르", required = true, dataType = "varchar", paramType = "path")})
	@GetMapping("/searchByRecommand")
	public Page<Boardgame> searchBoardgameByRecommand(SearchBoardGameByRecommand searchBoardGameByRecommand,
		Pageable pageable) {
		return boardGameService.searchBoardgameByRecommand(searchBoardGameByRecommand, pageable);
	}

	@ApiOperation(value = "보드게임을 리턴하는 메소드")
	@ApiImplicitParam(name = "boardgameId", value = "보드게임 id", required = true, dataType = "Long", paramType = "path")
	@GetMapping("/boardGameDetail/{boardgameId}")
	public BoardgameDetailDto selectBoardgameInfo(@PathVariable Long boardgameId) {
		return boardGameService.selectBoardgameInfo(boardgameId);
	}

	@ApiOperation(value = "보드게임을 리턴하는 메소드")
	@GetMapping("/boardgameList")
	public Page<Boardgame> selectBoardgameList(Pageable pageable) {
		return boardGameService.selectBoardgameList(pageable);
	}

}
