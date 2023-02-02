package com.superboard.onbrd.tag.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.tag.entity.FavoriteTag;

public interface FavoriteTagRepository extends JpaRepository<FavoriteTag, Long> {
	List<FavoriteTag> findAllByMember(Member member);
}
