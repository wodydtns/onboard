package com.superboard.onbrd.report.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.auth.entity.MemberDetails;
import com.superboard.onbrd.report.dto.ReportCreateCommand;
import com.superboard.onbrd.report.dto.ReportPostRequest;
import com.superboard.onbrd.report.entity.Report;
import com.superboard.onbrd.report.service.ReportService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "REPORT")
@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@Validated
public class ReportController {
	private final ReportService reportService;

	@Tag(name = "REPORT")
	@ApiOperation(value = "게시물 신고 / type REVIEW: 리뷰 신고, COMMENT: 댓글 신고")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer ...", required = true, dataTypeClass = String.class)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "생성 신고 ID 응답", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class), examples = {
			@ExampleObject(value = "1")})),
		@ApiResponse(responseCode = "404")})
	@ResponseStatus(CREATED)
	@PostMapping
	public ResponseEntity<Long> postReport(
		@AuthenticationPrincipal MemberDetails memberDetails, ReportPostRequest request) {
		Report created = reportService.createReport(
			ReportCreateCommand.of(memberDetails.getEmail(), request));

		return ResponseEntity
			.status(CREATED)
			.body(created.getId());
	}
}
