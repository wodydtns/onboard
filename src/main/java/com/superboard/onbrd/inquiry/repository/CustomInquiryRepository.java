package com.superboard.onbrd.inquiry.repository;

import com.superboard.onbrd.admin.dto.AdminInquiryDetail;
import com.superboard.onbrd.global.dto.OnbrdSliceRequest;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.inquiry.dto.InquiryGetResponse;
import com.superboard.onbrd.inquiry.dto.InquiryMyListResponse;

public interface CustomInquiryRepository {
	InquiryGetResponse getInquiryResponse(Long id);

	InquiryMyListResponse getMyInquiries(String email);

	OnbrdSliceResponse<AdminInquiryDetail> getAdminInquiries(OnbrdSliceRequest params);
}
