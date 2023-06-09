package com.superboard.onbrd.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superboard.onbrd.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>, CustomMemberRepository {
	Optional<Member> findByEmail(String email);

	boolean existsByEmail(String email);

	boolean existsByNickname(String nickname);
}
