package com.superboard.onbrd.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.entity.Password;

public interface PasswordRepository extends JpaRepository<Password, Long> {
	Optional<Password> findByMember(Member member);
}
