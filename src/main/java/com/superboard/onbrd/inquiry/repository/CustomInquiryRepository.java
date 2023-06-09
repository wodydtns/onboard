package com.superboard.onbrd.inquiry.repository;

import com.superboard.onbrd.admin.dto.AdminInquiryDetail;
import com.superboard.onbrd.global.dto.OnbrdSliceRequest;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.inquiry.dto.InquiryGetQuery;
import com.superboard.onbrd.inquiry.dto.InquiryGetResponse;

public interface CustomInquiryRepository {
	InquiryGetResponse getInquiryResponse(Long id);

	OnbrdSliceResponse<InquiryGetResponse> getMyInquiries(InquiryGetQuery query);

	OnbrdSliceResponse<AdminInquiryDetail> getAdminInquiries(OnbrdSliceRequest params);
}
