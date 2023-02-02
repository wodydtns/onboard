package com.superboard.onbrd.boardgame.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.superboard.onbrd.tag.entity.BoardgameTag;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Boardgame {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String description;
	@Column(nullable = false)
	private String image;
	@Column(nullable = false)
	private String playerCount;
	@Column(nullable = false)
	private String playtime;
	@Column(nullable = false)
	private String age;
	@Column(nullable = false)
	private String difficulty;
	@Column(nullable = false)
	private long favoriteCount;
	
	@OneToMany(mappedBy = "boardgame")
	private List<BoardgameTag> boardgameTags = new ArrayList<>();
	
}
