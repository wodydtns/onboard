package com.superboard.onbrd.boardgame.entity;

import com.superboard.onbrd.tag.entity.BoardgameTag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Boardgame")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
		name="BOARDGAME_SEQ_GENERATOR",
		sequenceName = "BOARDGAME_SEQ",
		initialValue = 1, allocationSize = 1
)
public class Boardgame {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator = "BOARDGAME_SEQ_GENERATOR" )
	private Long id;
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private String image;
	
	@Column(nullable = false)
	private long favoriteCount = 0;
	
	@Column(nullable = false)
	private long clickCount = 0;
	
	@OneToMany(mappedBy = "boardgame")
	private List<BoardgameTag> boardgameTags = new ArrayList<>();
}
