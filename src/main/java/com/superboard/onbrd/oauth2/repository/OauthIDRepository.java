package com.superboard.onbrd.oauth2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.oauth2.entity.OauthID;

public interface OauthIDRepository extends JpaRepository<OauthID, Long> {
	Optional<OauthID> findByMember(Member member);
}
