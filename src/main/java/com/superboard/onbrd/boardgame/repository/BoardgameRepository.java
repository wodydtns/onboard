package com.superboard.onbrd.boardgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superboard.onbrd.boardgame.entity.Boardgame;

public interface BoardgameRepository extends JpaRepository<Boardgame, Long>, CustomBoardgameRepository {
}
