package com.superboard.onbrd.inquiry.service;

import com.superboard.onbrd.admin.dto.AdminInquiryDetail;
import com.superboard.onbrd.admin.dto.InquiryAnswerCommand;
import com.superboard.onbrd.global.dto.OnbrdSliceRequest;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.inquiry.dto.InquiryCreateCommand;
import com.superboard.onbrd.inquiry.dto.InquiryGetResponse;
import com.superboard.onbrd.inquiry.dto.InquiryMyListResponse;
import com.superboard.onbrd.inquiry.dto.InquiryUpdateCommand;
import com.superboard.onbrd.inquiry.entity.Inquiry;

public interface InquiryService {
	InquiryMyListResponse getMyInquiries(String email);

	OnbrdSliceResponse<AdminInquiryDetail> getAdminInquiries(OnbrdSliceRequest params);

	InquiryGetResponse getInquiryResponse(Long id);

	Inquiry createInquiry(InquiryCreateCommand command);

	Long updateInquiry(InquiryUpdateCommand command);

	Inquiry answerInquiry(InquiryAnswerCommand command);

	Inquiry findVerifiedOneById(Long id);

}
