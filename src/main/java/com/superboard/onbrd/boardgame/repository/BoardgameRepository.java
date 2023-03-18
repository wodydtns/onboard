package com.superboard.onbrd.boardgame.repository;

import com.superboard.onbrd.boardgame.entity.Boardgame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardgameRepository extends JpaRepository<Boardgame,Long> {
}
