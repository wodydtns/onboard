package com.superboard.onbrd.member.service;

import static com.superboard.onbrd.global.exception.ExceptionCode.*;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.superboard.onbrd.admin.dto.AdminMemberDetail;
import com.superboard.onbrd.auth.entity.Token;
import com.superboard.onbrd.auth.service.TokenService;
import com.superboard.onbrd.global.exception.BusinessLogicException;
import com.superboard.onbrd.global.exception.OnbrdAssert;
import com.superboard.onbrd.member.dto.member.SignUpRequest;
import com.superboard.onbrd.member.dto.password.PasswordCreateCommand;
import com.superboard.onbrd.member.entity.Badge;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.entity.MemberRole;
import com.superboard.onbrd.member.entity.MemberStatus;
import com.superboard.onbrd.member.repository.MemberRepository;
import com.superboard.onbrd.oauth2.dto.OauthSignUpRequest;
import com.superboard.onbrd.tag.service.FavoriteTagService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberRepository memberRepository;
	private final PasswordService passwordService;
	private final FavoriteTagService favoriteTagService;
	private final TokenService tokenService;

	@Override
	public Member signUp(SignUpRequest request) {
		checkEmailDuplicated(request.getEmail());

		Member created = memberRepository.save(Member.from(request));

		passwordService.createPassword(PasswordCreateCommand.of(created, request.getPassword()));
		tokenService.createToken(Token.from(created));
		favoriteTagService.createdFavoriteTags(created, request.getTagIds());

		return created;
	}

	@Override
	public Member signUpFromOauth(OauthSignUpRequest request) {
		checkEmailDuplicated(request.getEmail());

		Member created = memberRepository.save(Member.fromOauth(request));

		tokenService.createToken(Token.from(created));
		favoriteTagService.createdFavoriteTags(created, request.getTagIds());

		return created;
	}

	@Override
	@Transactional(readOnly = true)
	public Member findVerifiedOneById(Long id) {
		return memberRepository.findById(id)
			.orElseThrow(
				() -> new BusinessLogicException(MEMBER_NOT_FOUND)
			);
	}

	@Override
	@Transactional(readOnly = true)
	public Member findVerifiedOneByEmail(String email) {
		return memberRepository.findByEmail(email)
			.orElseThrow(
				() -> new BusinessLogicException(MEMBER_NOT_FOUND)
			);
	}

	@Override
	public void checkMemberStatus(Member member) {
		switch (member.getStatus()) {
			case SUSPENDED:
				throw new BusinessLogicException(SUSPENDED_MEMBER);

			case WITHDRAWN:
				throw new BusinessLogicException(WITHDRAWN_MEMBER);

			case KICKED:
				throw new BusinessLogicException(KICKED_MEMBER);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public void checkDuplicatedNickname(String nickname) {
		if (memberRepository.existsByNickname(nickname)) {
			throw new BusinessLogicException(DUPLICATED_NICKNAME,
				String.format("Nickname %s is duplicated", nickname));
		}
	}

	@Override
	@Transactional(readOnly = true)
	public void checkDuplicatedEmail(String email) {
		if (memberRepository.existsByEmail(email)) {
			throw new BusinessLogicException(DUPLICATED_EMAIL,
				String.format("Email %s is duplicated", email));
		}
	}

	@Override
	public void checkEmailExists(String email) {
		Assert.isTrue(memberRepository.existsByEmail(email), "NOT_SIGNED_EMAIL");
	}

	@Override
	public void deleteMemberById(Long id) {
		Member member = findVerifiedOneById(id);
		memberRepository.delete(member);
	}

	@Override
	public Optional<Member> findByEmail(String email) {
		return memberRepository.findByEmail(email);
	}

	@Override
	public Member suspendMember(Long id) {
		Member member = findVerifiedOneById(id);
		MemberStatus status = member.getStatus();

		OnbrdAssert.state(status != MemberStatus.KICKED, SUSPEND_KICKED_MEMBER_NOT_PERMITTED);
		OnbrdAssert.state(status != MemberStatus.WITHDRAWN, SUSPEND_WITHDRAWN_MEMBER_NOT_PERMITTED);

		member.suspend();

		return member;
	}

	@Override
	public Member kickMember(Long id) {
		Member member = findVerifiedOneById(id);

		member.kick();

		return member;
	}

	@Override
	@Transactional(readOnly = true)
	public AdminMemberDetail getAdminMemberDetail(Long id) {
		return memberRepository.getAdminMemberDetail(id);
	}

	@Override
	public Member grantAdminAuthority(String email) {
		Member member = findVerifiedOneByEmail(email);
		member.gainAuthority(MemberRole.ROLE_ADMIN);

		return member;
	}

	@Override
	public Member grantBadges(Long id, Set<Badge> badges) {
		Member member = findVerifiedOneById(id);
		member.gainBadges(badges);

		return member;
	}

	private boolean isEmailExists(String email) {
		return memberRepository.findByEmail(email).isPresent();
	}

	private void checkEmailDuplicated(String email) {
		if (isEmailExists(email)) {
			throw new BusinessLogicException(DUPLICATED_EMAIL, String.format("Email %s is duplicated", email));
		}
	}
}
