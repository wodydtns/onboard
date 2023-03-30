package com.superboard.onbrd.inquiry.repository;

import com.superboard.onbrd.admin.dto.AdminInquiryDetail;
import com.superboard.onbrd.global.dto.OnbrdPageRequest;
import com.superboard.onbrd.global.dto.OnbrdPageResponse;
import com.superboard.onbrd.inquiry.dto.InquiryGetResponse;
import com.superboard.onbrd.inquiry.dto.InquiryMyListResponse;

public interface CustomInquiryRepository {
	InquiryGetResponse getInquiryResponse(Long id);

	InquiryMyListResponse getMyInquiries(String email);

	OnbrdPageResponse<AdminInquiryDetail> getAdminInquiries(OnbrdPageRequest params);
}
