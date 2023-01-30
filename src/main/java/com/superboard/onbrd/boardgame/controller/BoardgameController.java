package com.superboard.onbrd.boardgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.boardgame.entity.BoardGameSearchByRecommand;
import com.superboard.onbrd.boardgame.entity.Boardgame;
import com.superboard.onbrd.boardgame.service.BoardGameService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/boardgame")
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
		 @ApiImplicitParam(name = "name", value = "장르", required = true, dataType = "varchar", paramType = "path")
	 })
	@GetMapping("/searchByRecommand")
	public ResponseEntity<Boardgame> BoardGameSearchByRecommand(BoardGameSearchByRecommand boardGameSearchByRecommand) {
		return boardGameService.BoardGameSearchByRecommand(boardGameSearchByRecommand);
	}
}
