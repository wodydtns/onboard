package com.superboard.onbrd.tag.entity;

import javax.persistence.*;

import com.superboard.onbrd.boardgame.entity.BoardGame;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardGameTag {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "boardgame_id")
	private BoardGame boardGame;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tag_id")
	private Tag tag;


	@Builder
	public BoardGameTag(BoardGame boardGame, Tag tag){
		this.boardGame = boardGame;
		this.tag = tag;
	}

}
