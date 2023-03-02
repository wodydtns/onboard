package com.superboard.onbrd.boardgame.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.boardgame.dto.BoardgameDetailDto;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagRequest;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagResponse;
import com.superboard.onbrd.boardgame.dto.TopBoardgameDto;
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

	@ApiOperation(value = "보드게임 검색")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "tagIds", value = "태그 ID 리스트", required = true, dataType = "List", paramType = "path"),
		@ApiImplicitParam(name = "page", value = "페이지번호", required = true, dataType = "int", paramType = "path")})
	@GetMapping("/searchByRecommand")
	public List<BoardgameSearchByTagResponse.BoardGameResponse> searchBoardgameByRecommand(BoardgameSearchByTagRequest boardgameSearchByTagRequest) {
		return boardGameService.searchBoardgameByRecommand(boardgameSearchByTagRequest);
	}

	@ApiOperation(value = "보드게임 상세")
	@ApiImplicitParam(name = "boardgameId", value = "보드게임 id", required = true, dataType = "Long", paramType = "path")
	@GetMapping("/{boardgameId}")
	public BoardgameDetailDto selectBoardgameInfo(@PathVariable Long boardgameId, HttpServletRequest  request) {
		String referer = request.getHeader("Referer");
		if(ObjectUtils.isEmpty(referer)) {
			referer  = "";
		}
		return boardGameService.selectBoardgameInfo(boardgameId,referer);
	}

	@ApiOperation(value = "추천 보드게임")
	@GetMapping("/curation")
	@ApiImplicitParam(name = "page", value = "페이지번호", required = true, dataType = "int", paramType = "path")
	public List<BoardgameSearchByTagResponse.BoardGameResponse> selectRecommandBoardgameList(BoardgameSearchByTagRequest boardgameSearchByTagRequest) {
		return boardGameService.selectRecommandBoardgameList(boardgameSearchByTagRequest);
	}
	
	@ApiOperation(value = "보드게임 좋아요 증가")
	@PatchMapping("/updateFavorite")
	@ApiImplicitParam(name = "boardgameId", value = "보드게임 id", required = true, dataType = "Long", paramType = "path")
	public ResponseEntity<Void> updateFavoriteCount(Long id) {
		long count = boardGameService.updateFavoriteCount(id);
		if(count > 0) {
			return ResponseEntity.status(HttpStatus.OK).build();
			
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	@ApiOperation(value = "보드게임 검색순위 top 10")
	@GetMapping("/top10")
	public  List<TopBoardgameDto> selectTop10BoardgameList(){
		return boardGameService.selectTop10BoardgameList();
	}
}
