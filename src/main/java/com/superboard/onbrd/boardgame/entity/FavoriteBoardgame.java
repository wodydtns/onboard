package com.superboard.onbrd.boardgame.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FavoriteBoardgame {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
}
