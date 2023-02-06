package com.superboard.onbrd.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superboard.onbrd.member.dto.mypage.MypageGetDto;
import com.superboard.onbrd.member.dto.mypage.MypageGetMoreDto;
import com.superboard.onbrd.member.dto.mypage.MypageMoreBoardgameResponse;
import com.superboard.onbrd.member.dto.mypage.MypageMoreReviewResponse;
import com.superboard.onbrd.member.dto.mypage.MypageResponse;
import com.superboard.onbrd.member.dto.mypage.ProfileUpdateRequest;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.repository.MypageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MypageServiceImpl implements MypageService {
	private final MypageRepository mypageRepository;
	private final MemberService memberService;
	private final PasswordService passwordService;

	@Override
	@Transactional(readOnly = true)
	public MypageResponse getMypage(MypageGetDto params) {
		return mypageRepository.getMypage(params);
	}

	@Override
	@Transactional(readOnly = true)
	public MypageMoreReviewResponse getMoreReviews(MypageGetMoreDto params) {
		return mypageRepository.getMoreReviews(params);
	}

	@Override
	@Transactional(readOnly = true)
	public MypageMoreBoardgameResponse getMoreFavoriteBoardgames(MypageGetMoreDto params) {
		return mypageRepository.getMoreFavoriteBoardgames(params);
	}

	@Override
	public void updateProfile(String email, ProfileUpdateRequest request) {
		Member updated = memberService.findVerifiedOneByEmail(email);
		updated.updateProfileCharacter(request.getProfileCharacter());
		updated.updateNickname(request.getNickname());
	}

	@Override
	public void changePassword(String email, String password) {
		Member updated = memberService.findVerifiedOneByEmail(email);
		passwordService.resetPassword(updated, password);
	}

	@Override
	public void withDrawByEmail(String email) {
		Member member = memberService.findVerifiedOneByEmail(email);
		member.withdraw();
	}
}
