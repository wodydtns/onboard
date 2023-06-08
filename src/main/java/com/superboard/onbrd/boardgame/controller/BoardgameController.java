package com.superboard.onbrd.boardgame.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.auth.entity.MemberDetails;
import com.superboard.onbrd.boardgame.dto.BoardGameDetailDto;
import com.superboard.onbrd.boardgame.dto.BoardGameSearchDetail;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagRequest;
import com.superboard.onbrd.boardgame.dto.TopBoardgameDto;
import com.superboard.onbrd.boardgame.service.BoardGameService;
import com.superboard.onbrd.boardgame.service.FavoriteBoardGameService;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/boardgames")
@RequiredArgsConstructor
@Validated
@Api(tags = {"BoardGame 정보를 제공하는 Controller"})
public class BoardgameController {
	private final BoardGameService boardGameService;
	private final FavoriteBoardGameService favoriteBoardGameService;

	@ApiOperation(value = "보드게임 검색")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "tagIds", value = "태그 ID 리스트", required = false, dataType = "List", paramType = "query"),
		@ApiImplicitParam(name = "name", value = "보드게임 이름", required = false, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "offset", value = "페이지 시작", required = true, dataType = "int", paramType = "query"),
		@ApiImplicitParam(name = "limit", value = "페이지 끝", required = true, dataType = "int", paramType = "query"),
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "검색 성공"),
		@ApiResponse(code = 404, message = "검색 실패"),
		@ApiResponse(code = 500, message = "서버 에러")
	})
	@GetMapping("/searchBoardgameList")
	public ResponseEntity<OnbrdSliceResponse<BoardGameSearchDetail>> searchBoardgameList(
		BoardgameSearchByTagRequest boardgameSearchByTagRequest) {

		OnbrdSliceResponse<BoardGameSearchDetail> response =
			boardGameService.searchBoardgameList(boardgameSearchByTagRequest);

		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "보드게임 상세")
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 404, message = "잘못된 요청"),
		@ApiResponse(code = 500, message = "서버 에러")
	})
	@GetMapping(value = "/{boardgameId}")
	public ResponseEntity<BoardGameDetailDto> selectBoardgameInfo(
		@PathVariable Long boardgameId, HttpServletRequest request,
		@AuthenticationPrincipal MemberDetails memberDetails) {

		String referer = request.getHeader("Referer");
		if (ObjectUtils.isEmpty(referer)) {
			referer = "";
		}

		BoardGameDetailDto boardGameDetailDto = boardGameService.selectBoardgameInfo(boardgameId, referer);

		try {
			Boolean isFavorite = favoriteBoardGameService.isFavoriteBy(boardgameId, memberDetails.getEmail());
			boardGameDetailDto.setIsFavorite(isFavorite);
		} catch (NullPointerException e) {
			boardGameDetailDto.setIsFavorite(false);
		}

		return ResponseEntity.ok(boardGameDetailDto);
	}

	@ApiOperation(value = "추천 보드게임")
	@GetMapping("/curation")
	@ApiImplicitParam(name = "page", value = "페이지번호", required = true, dataType = "int", paramType = "query")
	@ApiResponses({
		@ApiResponse(code = 200, message = "추천 보드게임 검색 성공"),
		@ApiResponse(code = 404, message = "잘못된 요청"),
		@ApiResponse(code = 500, message = "서버 에러"),
	})
	public ResponseEntity<OnbrdSliceResponse<BoardGameSearchDetail>> selectRecommandBoardgameList(
		BoardgameSearchByTagRequest boardgameSearchByTagRequest) {

		OnbrdSliceResponse<BoardGameSearchDetail> response = boardGameService.selectRecommandBoardgameList(
			boardgameSearchByTagRequest);

		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "보드게임 좋아요 증가")
	@PatchMapping("/updateFavorite")
	@ApiImplicitParam(name = "boardgameId", value = "보드게임 id", required = true, dataType = "Long", paramType = "path")
	public ResponseEntity<Void> updateFavoriteCount(Long id) {
		long count = boardGameService.updateFavoriteCount(id);
		if (count > 0) {
			return ResponseEntity.status(HttpStatus.OK).build();

		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@ApiOperation(value = "보드게임 검색순위 top 10")
	@GetMapping("/top10")
	public List<TopBoardgameDto> selectTop10BoardgameList() {
		return boardGameService.selectTop10BoardgameList();
	}
}
