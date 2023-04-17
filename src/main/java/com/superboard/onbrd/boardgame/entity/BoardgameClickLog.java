package com.superboard.onbrd.boardgame.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class BoardgameClickLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long clicklogId;
	
	@OneToOne
	@JoinColumn(name="id")
	private Boardgame boardgame;
	
	@Column(nullable = false)
	private LocalDateTime lastClickAt = LocalDateTime.now();
	
	@Column(nullable = false)
	private long clickCount = 0;
	
}
