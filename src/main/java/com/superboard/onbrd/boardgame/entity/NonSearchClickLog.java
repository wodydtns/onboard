package com.superboard.onbrd.boardgame.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@DynamicInsert
public class NonSearchClickLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne
	@JoinColumn(name = "id")
	private Boardgame boardgame;
	
	@Column(nullable = false)
	private LocalDateTime clickAt = LocalDateTime.now();
	
	@Column(nullable = false)
	private long clickCount = 1;
	
	@Column(nullable = false)
	private Long boardgameId;
	
}
