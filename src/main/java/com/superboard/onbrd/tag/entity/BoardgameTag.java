package com.superboard.onbrd.tag.entity;

import javax.persistence.*;

import com.superboard.onbrd.boardgame.entity.Boardgame;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardgameTag {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "boardgame_id")
	private Boardgame boardgame;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tag_id")
	private Tag tag;


	@Builder
	public BoardgameTag(Boardgame boardgame, Tag tag){
		this.boardgame = boardgame;
		this.tag = tag;
	}

}
