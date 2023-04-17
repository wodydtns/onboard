package com.superboard.onbrd.boardgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superboard.onbrd.boardgame.entity.BoardGame;

public interface BoardgameRepository extends JpaRepository<BoardGame, Long>, CustomBoardgameRepository {
}
