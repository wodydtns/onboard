package com.superboard.onbrd.boardgame.controller;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.groovy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.bmc.http.client.HttpRequest;
import com.superboard.onbrd.boardgame.dto.BoardgameDetailDto;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagRequest;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagResponse;
import com.superboard.onbrd.boardgame.dto.RecommandBoardgameDto;
import com.superboard.onbrd.boardgame.service.BoardGameService;

import antlr.StringUtils;
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
	public Page<BoardgameSearchByTagResponse.BoardGameResponse> searchBoardgameByRecommand(BoardgameSearchByTagRequest boardgameSearchByTagRequest,
		@PageableDefault(size = 5) Pageable pageable) {
		return boardGameService.searchBoardgameByRecommand(boardgameSearchByTagRequest, pageable);
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
	@GetMapping("/recommandBoardgame")
	@ApiImplicitParam(name = "page", value = "페이지번호", required = true, dataType = "int", paramType = "path")
	public Page<RecommandBoardgameDto> selectRecommandBoardgameList(@PageableDefault(size = 3) Pageable pageable) {
		return boardGameService.selectRecommandBoardgameList(pageable);
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

}
