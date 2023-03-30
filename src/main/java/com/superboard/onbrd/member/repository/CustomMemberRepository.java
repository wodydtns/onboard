package com.superboard.onbrd.member.repository;

import com.superboard.onbrd.admin.dto.AdminMemberDetail;

public interface CustomMemberRepository {
	AdminMemberDetail getAdminMemberDetail(Long id);
}
