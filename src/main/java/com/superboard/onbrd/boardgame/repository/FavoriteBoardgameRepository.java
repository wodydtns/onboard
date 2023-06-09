package com.superboard.onbrd.boardgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superboard.onbrd.boardgame.entity.FavoriteBoardgame;

public interface FavoriteBoardgameRepository extends JpaRepository<FavoriteBoardgame, Long> {
	boolean existsByBoardgame_IdAndMember_Email(Long boardgameId, String memberEmail);
}
