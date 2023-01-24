package com.superboard.onbrd.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superboard.onbrd.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByEmail(String email);

	Optional<Member> findByNickname(String nickname);
}
