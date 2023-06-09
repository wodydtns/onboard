package com.superboard.onbrd.inquiry.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.auth.entity.MemberDetails;
import com.superboard.onbrd.global.dto.OnbrdSliceRequest;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.inquiry.dto.InquiryCreateCommand;
import com.superboard.onbrd.inquiry.dto.InquiryGetQuery;
import com.superboard.onbrd.inquiry.dto.InquiryGetResponse;
import com.superboard.onbrd.inquiry.dto.InquiryPatchRequest;
import com.superboard.onbrd.inquiry.dto.InquiryPostRequest;
import com.superboard.onbrd.inquiry.dto.InquiryUpdateCommand;
import com.superboard.onbrd.inquiry.entity.Inquiry;
import com.superboard.onbrd.inquiry.service.InquiryService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Inquiry")
@RestController
@RequestMapping("/api/v1/inquiries")
@RequiredArgsConstructor
@Validated
public class InquiryController {
	private final InquiryService inquiryService;

	@Tag(name = "Inquiry")
	@ApiOperation(value = "1:1 문의 조회")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer ...", required = true, dataTypeClass = String.class)
	@GetMapping
	public ResponseEntity<OnbrdSliceResponse<InquiryGetResponse>> getMyInquiries(
		@AuthenticationPrincipal MemberDetails memberDetails, @ModelAttribute OnbrdSliceRequest request) {
		request.rebaseToZero();

		OnbrdSliceResponse<InquiryGetResponse> response = inquiryService.getMyInquiries(InquiryGetQuery.of(
			memberDetails.getEmail(), request.getOffset(), request.getLimit()));

		return ResponseEntity.ok(response);
	}

	@Tag(name = "Inquiry")
	@ApiOperation(value = "내가 작성한 1:1 문의 목록 조회")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer ...", required = true, dataTypeClass = String.class)
	@GetMapping("/{id}")
	public ResponseEntity<InquiryGetResponse> getInquiry(@PathVariable Long id) {
		InquiryGetResponse response = inquiryService.getInquiryResponse(id);

		return ResponseEntity.ok(response);
	}

	@Tag(name = "Inquiry")
	@ApiOperation(value = "1:1 문의 작성")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer ...", required = true, dataTypeClass = String.class)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "생성 1:1 문의 ID 응답", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class), examples = {
			@ExampleObject(value = "1")})),
		@ApiResponse(responseCode = "404")})
	@ResponseStatus(CREATED)
	@PostMapping
	public ResponseEntity<Long> postInquiry(
		@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody InquiryPostRequest request) {

		Inquiry created = inquiryService.createInquiry(
			InquiryCreateCommand.of(memberDetails.getEmail(), request));

		return ResponseEntity
			.status(CREATED)
			.body(created.getId());
	}

	@Tag(name = "Inquiry")
	@ApiOperation(value = "1:1 문의 수정")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer ...", required = true, dataTypeClass = String.class)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "수정 1:1 문의 ID 응답", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class), examples = {
			@ExampleObject(value = "1")})),
		@ApiResponse(responseCode = "404")})
	@PatchMapping("/{id}")
	public ResponseEntity<Long> patchInquiry(@PathVariable Long id, @RequestBody InquiryPatchRequest request) {
		Long updatedId = inquiryService.updateInquiry(
			InquiryUpdateCommand.of(id, request));

		return ResponseEntity.ok(updatedId);
	}
}
