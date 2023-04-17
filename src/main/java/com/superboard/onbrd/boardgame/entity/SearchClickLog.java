package com.superboard.onbrd.boardgame.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@DynamicInsert
public class SearchClickLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private BoardGame boardGame;
	
	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime clickAt = LocalDateTime.now();
	
	@Column(nullable = false)
	private long clickCount = 1;
	
	@Column(nullable = false)
	private Long boardgameId;
}
