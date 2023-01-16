package com.superboard.onbrd.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.superboard.onbrd.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	
}
