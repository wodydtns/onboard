package com.superboard.onbrd.boardgame.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.DynamicInsert;

import lombok.Data;

@Data
@Entity
@DynamicInsert
public class NonSearchClickLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne
	@JoinColumn(name = "id")
	private Boardgame boardGame;

	@Column(nullable = false)
	private LocalDateTime clickAt = LocalDateTime.now();
	
	@Column(nullable = false)
	private long clickCount = 1;
	
	@Column(nullable = false)
	private Long boardGameId;
	
}
